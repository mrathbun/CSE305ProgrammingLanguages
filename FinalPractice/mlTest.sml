fun f1(x, y, z) = (x*2, y*z/3.14);

datatype 'a tree = leaf of 'a 
 | node of 'a tree * 'a tree 


fun f2(leaf(x)) = [x] (* datatype ‘a tree = leaf of ‘a *)
 | f2(node(t1,t2)) = f2(t1) @ f2(t2); (* | node of ‘a tree * ‘a tree *)
fun f3(x) = f3(x);
fun f4([ ]) = [ ]
 | f4(x::t) = x ^ " " ^ f4(t);
