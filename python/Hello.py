#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 由于有中文，需要加上上面这句，表示文本的编码是utf-8
# Hello.py

value = "Hello World"

print "100 + 300 =", 100 + 300
print value

# 分支语句
a = 20
if a > 0:
    print a
elif a > 20:
    print a * 10
else:
    print -a

print ord('A')

# 使用格式化字符串%s
print "字符串:%s 数字:%d 浮点数:%f 十六进制整数:%x" % (value, a, 12.3, 30)
print "字符串永远起作用:%s%s%s%s" % ('Hello', a, 21, 3.1)

# 数组
arr = [12, 32, "qq", 21]
print arr, " len:", len(arr)
arr.pop(1)
for x in xrange(0, len(arr)):
    print arr[x]
# tuple不可变数组
t_arr = (12, 21, 23)
print t_arr
for x in t_arr:
    print "tuple:", x
# 计算1～100的和
sum = 0
for x in range(1, 101):
    sum += x
print sum

# 字典（map）
x_dict = {'a':1,'b':'c','c':3}
print x_dict

# 函数
def max(x, y):
	if x > y:
		return x + y
	else:
		return y - x

print max(2, 12)

def getLocation():
	return -1, 2323
location = getLocation()
print location[0], location[1]

def power(x):
	return x
def power(x, n = 1):
	s = 1
	for i in xrange(0,n):
		s *= x
	return s
print power(3), power(3, 3)

# numbers作为可变变量
def sum(*numbers):
	s = 0
	for x in numbers:
		s += x
	return s
print sum(12,21),sum(23,43,12)

# 递归 阶乘
def factorial(n):
	if n <=1:
		return 1
	else:
		return n * factorial(n-1)
	pass
print factorial(100)

# 尾递归：函数返回时调用其本身，且return语句不包含表达式
def factorial(n, p):
	if n <= 1:
		return p
	else:
		return factorial(n-1, n*p)
def fact(n):
	return factorial(n, 1)
print fact(10)


# 输入
# name = raw_input("Enter your name:")
# print "Your name is", name
# age = int(raw_input("Enter your age:"))
# print "Your age is", age

