# Simple-earnings-date-scraper
---

# Goal
The goal of this project is to create a way to get all historical earnings dates for a symbol in the stock market. 

# What is does
It currently uses JSoup to scrape Zacks.com for all historical earnings dates for a ticker symbol submitted by the user, then it will write that data to a local database (default C:\EarningsDates\).

# How it works
Waits for the user to input a symbol

Once it has that symbol it will load https://www.zacks.com/stock/research/ + symbol + /earnings-announcements

It will do a simple check to make sure the page loaded correctly 

Then it will scrape the earnings dates

Finally, it will store the data in the database as symbol.csv and ask the user to run again
