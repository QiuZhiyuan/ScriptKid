# !/usr/bin/env python
# -*- coding: utf-8 -*-
from Tkinter import *
import tkMessageBox


class SimpleGui(Frame):
    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.pack()
        self.createWidgets()
        self.master.title('Hello')

    def createWidgets(self):
        self.helloLabel = Label(self, text='Hello World')
        self.helloLabel.pack()
        self.quitButton = Button(self, text='Quit', command=self.clickListener)
        self.quitButton.pack()
        self.xxx = Label(self, text='What\'s this')
        self.xxx.pack_configure()

    def clickListener(self):
        self.helloLabel = Label(self, text='Clicked')
        self.helloLabel.pack()


class SimpleInputGui(Frame):
    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.pack()
        self.createWidgets()

    def createWidgets(self):
        self.nameInput = Entry(self)
        self.nameInput.pack()
        self.alertButton = Button(self, text="Hello", font=('bold'), command=self.hello, width=5, height=1)
        self.alertButton.pack()

    def hello(self):
        name = self.nameInput.get() or 'World'
        tkMessageBox.showinfo("Message", "Hello, %s" % (name))


app = SimpleInputGui()
app.mainloop()
