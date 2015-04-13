# -*- coding:utf-8 

a="""
50 #CFF4FF
100 #9FDFFF
200 #83D0FF
300 #62B6F4
400 #4092DD
500 #2473C5
600 #1464AD
700 #1B5796
800 #004985
900 #003C6D
A100 #FFA310
A200 #FF850C
A400 #FF740D
A700 #FF6510
""".strip().split("\n")

for line in a:
  id,col=line.strip().split(" ")
  if id.startswith("A"):
    print '<color name="cw_accent_'+id+'">#FF'+col[1:]+'</color>'
  else:
    print '<color name="cw_normal_'+id+'">#FF'+col[1:]+'</color>'
