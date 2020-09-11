# !/usr/bin/env python
# -*- coding: utf-8 -*-

import socket

print 'Start connect to web'
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('www.sina.com.cn', 80))
print 'Start send data to service'
s.send('GET / HTTP/1.1\r\nHost: www.sina.com.cn\r\nConnection: close\r\n\r\n')
print 'Start recv data from service'
buffer = []
while True:
    d = s.recv(1024)
    if d:
        buffer.append(d)
    else:
        break
data = ''.join(buffer)
s.close()
print data
with open('cache/recv_from_sina', 'wb') as f:
    f.write(data)

header, html = data.split('\r\n\r\n', 1)
with open('cache/sina.html', 'wb') as f:
    f.write(html)
print header
