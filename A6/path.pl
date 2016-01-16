path(Start, End, [Start | P]) :- path2(Start, End, P, [Start]).
 
walkway2(X, Y) :- walkway(X, Y).
walkway2(X, Y) :- walkway(Y, X).

path2(X, Y, [Y], List) :- walkway2(X, Y),
                          \+member(Y, List).
path2(X, Y, [Z|P], List) :- walkway2(X, Z),
                      \+member(Z, List),
                      path2(Z, Y, P, [Z|List]). 

is_subset(S1, S2) :- \+q(S1, S2).


q(S1, S2) :- member(X, S1),
             \+member(X, S2).


pathvia(BList, B1, B2, Path) :- path(B1, B2, Path),
                                is_subset(BList, Path).
             

