#Mitchell Rathbun

def left_gen(a):
   for x in a[1:]: 
      if x < a[0]:
         yield x

def right_gen(a):
   for x in a[1:]:
      if x >= a[0]:
         yield x

def qsort(a):
   if a == []: return []
   left = []
   right = []
   for x in left_gen(a):
      left.append(x)
   for x in right_gen(a):
      right.append(x) 
   list = qsort(left) 
   list.append(a[0])
   list.extend(qsort(right))
   return list

print(qsort([5,4,6,3,7,9,2,8,1,0])) 
