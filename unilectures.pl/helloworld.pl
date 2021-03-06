mann(adam).
frau(eva).
kind(kain).
kind(abel).

ehepaar(adam, eva).
ehepaar2(X, Y):-
       frau(X),ehepaar(Y, X),! ; mann(X),ehepaar(X, Y).

contains(X,[Y|YS]):-
        (X=Y;contains(X, YS)).

laenge(L,Y):-
      L=[],Y=0;
      L=[_|XS], laenge(XS,Z), Y is Z + 1. 

dog(bello).
dog(lassie).
dog(goofy).
dog(pluto).
dog(catdog).

cat(damian).
cat(garfield).
cat(catdog).
cat(kitty).
cat(nadia).
cat(blackPete).

animal(X):-
      cat(X);dog(X).

hybrid(X):-
      cat(X),dog(X).