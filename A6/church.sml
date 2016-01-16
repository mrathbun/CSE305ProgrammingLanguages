datatype 'a inf_list = lcons of 'a * (unit -> 'a inf_list)

fun church(n) = let fun thk() = church("(f "^n^")")
                in lcons("Lf.Lx.(f "^n^")", thk)
                end;

fun zip(lcons(a, thk1), lcons(b, thk2)) = 
                      let fun thk() = zip(thk1(), thk2())
                      in lcons((a, b), thk)
                      end;   

fun nums(n) = let fun thk() = nums(n+1) in lcons(n, thk) end;
fun take(0, _) = []
 | take(n, lcons(h, thk)) = h :: take(n-1, thk());
 let val l1 = nums(1);
     val l2 = church("x")
 in take(5, zip(l1,l2))
end;
