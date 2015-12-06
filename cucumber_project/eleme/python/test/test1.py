# -*- coding:utf-8 -*-
import test2
import test3
import json
def test(*aaaa):
	dict = '{"aa":"adsf", "bb":"asf"}'
	dict = json.loads(dict)
	dict["aa"] = aaaa[0]
	dict["bb"] = aaaa[1]
	return json.dumps(dict)

def assert_test(*args):
	x = 23
	try:
		assert x%2 == 0, "x is not an even number"
	except Exception, msg:
		print msg
		return Exception(msg)
	# if not isinstance(x, int):
		# return NameError("not an int")
