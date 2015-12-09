# -*- coding:utf-8 -*-
import httplib2

def get_data(*args):
	h = httplib2.Http(".cache")
	resp, content = h.request("http://example.org/", "GET")
	print resp

