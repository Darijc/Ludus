#!/usr/bin/env python
# -*- coding: UTF-8 -*-
"""
Answers a twitter interview question.
See: http://qandwhat.apps.runkite.com/i-failed-a-twitter-interview/

@date 2013-10-31
@author: Bernhard Häussner
"""

class SliceIterator:
  """
  Allows us to walk over the landscape, retrieving the ground height and
  setting the water level for each slice.
  
  >>> i = [1, 2, 3, 4]
  >>> o = [0, 0, 0, 0]
  >>> it = SliceIterator(i, o)
  >>> it.get()
  1
  
  The output array is modified in place:
  
  >>> it.set(5)
  >>> o[0]
  5
  
  Advance the itarator:
  
  >>> it.next()
  >>> it.get()
  2
  
  The output pointer is advanced too:
  
  >>> it.set(6)
  >>> o[1]
  6
  
  Adjustable the step size and the start position:
  
  >>> it2 = SliceIterator(i, o, -2, 3)
  >>> it2.get()
  4
  >>> it2.set(7)
  >>> o[3]
  7
  >>> it2.next()
  >>> it2.get()
  2
  
  Compare two iterators, which one is further advanced:
  
  >>> cmp(it, it2)
  0
  >>> it == it2
  True
  
  >>> it.next()
  >>> cmp(it, it2)
  1
  >>> it > it2
  True
  
  """
  
  def __init__(self, inarray, outarray, step=1, start=0):
    self._inarray = inarray
    self._outarray = outarray
    self._step = step
    self._pointer = start
  
  def set(self, to):
    self._outarray[self._pointer] = to
  
  def get(self):
    return self._inarray[self._pointer]
  
  def next(self):
    self._pointer += self._step
  
  def __cmp__(self, other):
    return cmp(self._pointer, other._pointer)

class Landscape():
  """
  Represents a landscape, a list of profile heights.
  
  Generate random landscapes and simulate where the water would go if it rains.
  """
  
  def __init__(self, wallsizes):
    self.wallsizes = wallsizes

  def puddledepths(self):
    """
    Calculates the water between the walls.
    
    Consider this picture, where '~' represents water and '#' walls:
    
          77
          ##6
    5~~~~###
    #~~~4###
    #~~3####
    2#~2#####
    ##1######
    ---------
    012345678 (indices)
    
    We have a list of wall heights and want to know how much water would
    accumulate at any index in case of rain. The water on the sides spills out.
    For the above picture we would get:
    
    >>> Landscape([2, 5, 1, 2, 3, 4, 7, 7, 6]).puddledepths()
    [0, 0, 4, 3, 2, 1, 0, 0, 0]
    
    Let's consider another picture:
    
          77
          ##6
    5~~~~~###
    #~~~~~###
    #~3~~~###
    2#~#~2~###
    ##1#1#1###
    ----------
    0123456789 (indices)
    
    This is the accompanying calculation:
    
    >>> Landscape([2, 5, 1, 3, 1, 2, 1, 7, 7, 6]).puddledepths()
    [0, 0, 4, 2, 4, 3, 4, 0, 0, 0]
    
    There can be more than one puddle though. Consider this picture:
    
          7~7
          #~#6
    5~~~~~#~##
    #~~~~~#4##
    #~3~~~####
    2#~#~2~####
    ##1#1#1####
    -----------
    0123456789A (hex indices)
    
    This is the accompanying calculation:
    
    >>> Landscape([2, 5, 1, 3, 1, 2, 1, 7, 4, 7, 6]).puddledepths()
    [0, 0, 4, 2, 4, 3, 4, 0, 3, 0, 0]
    
    There can be no puddle at all. Consider this picture:
    
    3
    2#
    ##1
    ---
    012 (indices)
    
    This is the accompanying calculation:
    
    >>> Landscape([2, 3, 1]).puddledepths()
    [0, 0, 0]
    
    Also consider this picture:
    
    3
    2#~2
    ##1#1
    -----
    01234 (indices)
    
    This is the accompanying calculation:
    
    >>> Landscape([2, 3, 1, 2, 1]).puddledepths()
    [0, 0, 1, 0, 0]
    
    This is a basic puddle spawning over the whole landscape:
    
      3
    2~#
    #1#
    ----
    0123 (indices)
    
    This is the accompanying calculation:
    
    >>> Landscape([2, 1, 3]).puddledepths()
    [0, 1, 0]
    
    We want to handle empty puddles just fine:
    
    >>> Landscape([]).puddledepths()
    []
    
    """
    wallsizes = self.wallsizes
    waterlevels = ['*' for _ in wallsizes]
    frnt = SliceIterator(wallsizes, waterlevels)
    back = SliceIterator(wallsizes, waterlevels, -1, len(wallsizes) - 1)
    for i in [frnt, back]:
      i.max = 0 
    while frnt <= back:
      p = frnt if frnt.max < back.max else back
      if p.get() > p.max:
        p.max = p.get()
        p.set(0)
      else:
        p.set(p.max - p.get())
      p.next()
    return waterlevels

  def __str__(self):
    """
    String representation of a landscape with puddles.
    
    >>> str(Landscape([1, 2, 3, 1]))
    '  # \\n ## \\n####'
    
    >>> str(Landscape([1, 3, 2, 3, 1]))
    ' #~# \\n ### \\n#####'
    
    """
    wallsizes = self.wallsizes
    waterlevels = self.puddledepths()
    return '\n'.join([(''.join([('#' if wallsizes[k] >= i else ('~' if wallsizes[k]+waterlevels[k] >= i else ' ')) for k in range(len(wallsizes))])) for i in range(max(wallsizes),0,-1)])
  
  @staticmethod
  def random(length=80):
    """
    Generate a random landscape using a random walk in one dimension.
    
    Landscapes may not have negative values and no excess height:
    >>> all([min(Landscape.random().wallsizes) == 0 for _ in range(200)])
    True
    
    Landscapes of size 80 are default:
    >>> len(Landscape.random().wallsizes)
    80
    
    Generate landscapes of specific lengths:
    >>> len(Landscape.random(40).wallsizes)
    40
    
    """
    from random import randint
    wallsizes = [1]
    for i in range(1,length):
      wallsizes.append(wallsizes[-1] + randint(-2,2))
    m = min(wallsizes)
    wallsizes = map(lambda x: x-m, wallsizes)
    return Landscape(wallsizes)

if __name__ == '__main__':
  """
  Example output:
  
                            ###                                                   
                            ###                                                   
                 #~~~~~~~~######~~~~~~~#                                          
     #~~~~~~~~~~~###~~~~~~#######~~~~~##~#~~~~~~~##~~~~~~~~~~~~~~~~~~#            
     #~~~~~~~~~~####~~~~##########~~~~#####~~##~~##~~~~~~~~~~#~~~~~~##            
  #~###~~~~~~~#######~~~##########~~~###############~~~~~~~~~#~~~~#####~#~##      
  #~###~~~############~#############~###############~~~~~~~~####~~###########     
  ######~#############~##############################~#~#~~~#################     
  ######~#################################################~###################    
  ########################################################~###################~~##

  """
  import doctest
  doctest.testmod()
  
  print Landscape.random()


