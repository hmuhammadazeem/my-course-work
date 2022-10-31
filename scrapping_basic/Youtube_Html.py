from bs4 import BeautifulSoup
import csv

"""
# Semester: Fall 2018

import urllib.request as urllib2

import json

URL = '#'

page = urllib2.urlopen(URL)

"""

def get_views(line):
	index = 0
	if 'seconds' in line or 'second' in line:
		index = line.find('second')
	else:
		index = line.find('minutes')
	line = line[index+8:]
	line = line[:-6]
	line = line.split(',')
	line = ''.join(line)
	return int(line)
	
def get_duration(line):
	index = line.find('ago')
	line  = line[index+4:]
	
	if 'seconds' in line:
		s_ind = line.find('second')
		line = line[:-(abs(len(line) - (s_ind + 8)))]
		
	elif 'minutes' in line:
		m_ind = line.find('minutes')
		line  = line[:-(abs(len(line) - (m_ind + 8)))]
	
	elif 'hour' in line:
		h_ind = line.find('hour')
		line  = line[:-(abs(len(line) - (h_ind + 6)))]
		
	if 'hour' in line or 'minute' in line or 'seconds' in line or 'seconds' in line:
		line = line.split()
	
	if 'seconds' in line:
		line.remove('seconds')
	
	elif 'second' in line:
		line.remove('second')
	
	else:
		line.append('0')
	
	if 'minutes,' in line:
		line.remove('minutes,')
	
	elif 'minutes' in line:
		line.remove('minutes')
	
	elif 'minute,' in line:
		line.remove('minute,')
	
	elif 'minute' in line:
		line.remove('minute')
	
	else:
		line.insert(-2, '0')
	
	if 'hours,' in line:
		line.remove('hours,')
	
	elif 'hours' in line:
		line.remove('hours')
	
	elif 'hour,' in line:
		line.remove('hour,')
	
	elif 'hour' in line:
		line.remove('hour')
	
	else:
		line.insert(0, '0')

	return line
	
		
def main():
	base = 'https://youtube.com'	

	with open('YouTube.html', encoding="utf-8") as f:
		soup = BeautifulSoup(f, 'html.parser')

	items = soup.find_all('a', "yt-simple-endpoint style-scope ytd-video-renderer")

	items_csv = [[x['title'], base+x['href'], (':'.join(get_duration(x['aria-label']))), get_views(x['aria-label'])] for x in items]
	
	with open('YouTube_Trends.csv', 'w', newline='', encoding='utf-8') as csvfile:
		spamwriter = csv.writer(csvfile, delimiter=',')
		for item in items_csv:
			spamwriter.writerow(item)
		

if __name__ == '__main__':
	main()



		