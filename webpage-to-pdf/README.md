# webpage-to-pdf

Python tool that lets you save web pages to PDF files. It takes a list of websites as input. The script uses pyppeteer to serve the purpose.

        Usage: python web_to_pdf.py

        Choose a mode of operation and provide relevant input, and print 
        the output to a PDF file with a choice between different page sizes.

        Modes of operation:
            (1)     Save whole website to PDF unchanged
            (2)     Providing a list of which CSS divs to extract
            (3)     Providing useful defaults for making a website more printer-friendly

#Note: The tool was built with intention to just serve purpose. Just sharing here if anyone might find use in it. Also, the code is not #commented currently, and there is no exception handling. I might update it in future.
