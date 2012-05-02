#!/usr/bin/python

from __future__ import division

def main():
    key = []
    res = []
    with open ("key") as inputKey:
        key = [a for a in inputKey]
    
    with open ("res.dat") as inputRes:
        res = [a for a in inputRes]
    
    count = 0
    for i in range(0, len(key)):
        if key[i] != res[i]:
            print " - ".join([key[i],res[i],str(i)])
            count+=1
    print "error:",count/83
    
if __name__ == "__main__":
    main()