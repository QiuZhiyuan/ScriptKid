# -*- coding: utf-8 -*-
# Python的一些特性

# 切片，直接获取数组中的一部分元素
L = (11, 22, 33, 44, 55, 66, 77, 88, 99, 1010)
print L[2:4]
print L[-3:-1]
# 0~10 每两个取一个
print L[0:8:2]
# 1~末尾，每三个取一个
print L[1::3]

# 字符串也可以看作数组
Str = 'ABCDEFGHIJKLMNOP'
print Str[3:10:2]

