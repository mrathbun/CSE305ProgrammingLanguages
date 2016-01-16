#Mitchell Rathbun

def left_gen(a, thunk):
   for x in a[1:]:
      if x < a[0]:
         thunk(x)

def right_gen(a, thunk):
   for x in a[1:]:
      if x >= a[0]:
         thunk(x)

def qsort(a):
   if a == []: return []
   left = []
   right = []
   def thk1(x):
      left.append(x)
   def thk2(x):
      right.append(x)
   left_gen(a, thk1)
   right_gen(a, thk2)
   list = qsort(left)
   list.append(a[0])
   list.extend(qsort(right))
   return list

print(qsort([5,4,6,3,7,9,2,8,1,0]))

