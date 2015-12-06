# -*- coding:utf-8 -*-
import httplib2

def get_data(aaa):
	h = httplib2.Http(".cache")
	resp, content = h.request("http://example.org/", "GET")
	print resp
	return str(aaa)+'aaaaaaa'

