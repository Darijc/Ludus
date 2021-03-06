
def deaDefineSuche(Sigma,v):
    delta,Z,F={},set(range(len(v)+1)),{len(v)}
    for i,b in ((i,b) for i in range(len(v)) for b in Sigma):
        delta[i,b]=max([0]+[k for k in range(0,i+2) if (v[:i]+b)[-k:]==v[:k]])
    for b in Sigma:delta[len(v),b]=len(v)
    return [Sigma,Z,delta,0,F]
def deaErweiterteUEF(delta,z,w):
    for a in w:z=delta[z,a]
    return z
def deaRun(A, w):
    return deaErweiterteUEF(*A[-3:-1]+[w]) in A[4]
def schnelleSuche(v,w):
    """Return if string v can be found in string w using a DEA.
    
    >>> schnelleSuche("abc","aaddababcaa")
    True
    >>> schnelleSuche("abc","aaddababaca")
    False
    >>> schnelleSuche("abcabd","aaadaddacdcadabcabcabdaac")
    True
    >>> schnelleSuche("","aaddababcaa")
    True
    >>> schnelleSuche("abc","")
    False
    """
    return deaRun(deaDefineSuche(set(v)|set(w),v),w)

if __name__ == '__main__': # Tests:
    import doctest
    doctest.testmod(verbose=True)

