Feature: Search a Product, verify Sorting and pagination
@sort
Scenario: Search and verify results
Given I am a non-registered customer
And I navigate to www.ebay.co.uk
When I search for an item
Then I get a list of matching results
And the resulting items cards show: postage price, No of bids, price or show BuyItNow tag
When I sort the results by Lowest Price
Then the results are listed in the page in the lowest to Highest order
When I sort the results by Highest Price
Then the results are listed in the page in the Highest to lowest order

@navigate
Scenario: Search and navigate through results pages
Given I am a non-registered customer
And I navigate to www.ebay.co.uk
When I search for an item
Then I get a list of matching results
And the results show more than one page
And the user can navigate through the pages to continue looking at the items