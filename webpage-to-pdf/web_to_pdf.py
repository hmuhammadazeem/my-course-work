import asyncio
from pyppeteer import launch
from bs4 import BeautifulSoup
import requests as rq

async def get_pdf(url, page_size):
    browser = await launch()
    page = await browser.newPage()
    await page.setViewport({ 'width': 1440, 'height': 900, 'deviceScaleFactor': 2})
    await page.goto(url, {'waitUntil': "networkidle2"})
    title = await page.title()
    await page.pdf({'path': (str(title) + '.pdf'), 'format': page_size, 'printBackground': True})
    await browser.close()
    

async def get_pdf_from_content(content, title):
    browser = await launch()
    page = await browser.newPage()
    await page.setViewport({ 'width': 1440, 'height': 900, 'deviceScaleFactor': 2})
    await page.setContent(content)
    await page.pdf({'path': (title + '.pdf'), 'format': "A4", 'printBackground': True})
    await browser.close()
    
def main():
    interface = """
    #######
    
        A Python tool that lets you save web pages to PDF files. It takes a list of websites as input.
        The script uses pyppeteer to serve the purpose.

        Usage: python web_to_pdf.py

        Provide a list of websites, choose a mode of operation and provide relevant input, and print 
        the output to a PDF file with a choice between different page sizes.

        Modes of operation:
            (1)     Save whole website to PDF unchanged
            (2)     Providing a list of which CSS divs to extract
            (3)     Providing useful defaults for making a website more printer-friendly
            
    #######
    """
    
    print(interface)
    
    opted = int(input('Input(1,2 or 3): '))
    
    if opted == 1:
        print("""Enter the path to the file containing URLs list. e.g. C:\site.txt"""
              + """\nFile format: One url per line.""")
        file = input("Enter the path: ")
        file = open(file, 'r')
        for url in file.readlines():
            asyncio.get_event_loop().run_until_complete(get_pdf(url, 'A4'))
        print('Operation completed successfuly.')
    elif opted == 2:
        print("""Enter the URLs in file without https://. e.g. google.com; One URL per line."""
              + """ Also input the, IDs of DIVs to extract. IDs format: ID1,ID2 and so on.\n"""
              + "The final document will be sequentialy based on IDs list.")
        file = input('File Path: ')
        file = open(file, 'r')
        urls = file.readlines()
        for i in range(0, len(urls)):
            ids = input('IDs for `' + str(urls[i]) + '`:\n>>> ').split(',')
            doc = "<html><body>"
            doc += soup.find('head')
            page = (rq.get('http://' + urls[i])).text       
            soup = BeautifulSoup(page, 'html.parser')
            title = soup.find('title').text
            for id in ids:
                tag = soup.find("div", {"id": id})
                doc += str(tag)
            doc += "</body></html>"
            asyncio.get_event_loop().run_until_complete(get_pdf_from_content(doc, title))        
    elif opted == 3:
        print("""This mode uses page size as Letter. Enter the path to the file containing URLs list. e.g. C:\site.txt"""
              + """\nFile format: One url per line.""")
        file = input("Enter the path: ")
        file = open(file, 'r')
        for url in file.readlines():
            asyncio.get_event_loop().run_until_complete(get_pdf(url, 'Letter'))
        print('Operation completed successfuly.')
    else:
        print('Wrong option!')
        quit()
    
    
if __name__ == '__main__':
    main()
