Hirirng Exercise - Multi RSS Analyzer

Steps by using software:

1) Run the software:

	Please check how to install maven in Windows using this URL: https://howtodoinjava.com/maven/how-to-install-maven-on-windows/
	
	Please check how to install maven in Linux or Unix using this URL: https://maven.apache.org/install.html

	After the last steps Run results really easy, you just run the program into the github repository and run the next command with Maven:	
	
	# mvn spring-boot:run
	
	Once this compile and runs, please you can do the next step

2) Execute the analysis of various RSS Feed
	
	This endpoind exposes a REST Web service with a context that receives a list of URL RSS Feed in JSon Format and finally shows an ID in JSon Format.
	
	How it works:

	Please Send via POST the RSS URL list.
	
	POST URL: http://localhost:8080/analyse/new
	
	POST MESAGE Example:

	[
		"http://feeds.bbci.co.uk/news/world/rss.xml",
		"http://www.cbn.com/cbnnews/world/feed/",
		"http://feeds.reuters.com/Reuters/worldNews",
		"https://www.dailytelegraph.com.au/help-rss",
		"http://www.weatherzone.com.au/services/rss.jsp",
		"https://thewest.com.au/rss-feeds",
		"http://feeds.bbci.co.uk/news/rss.xml",
		"http://feeds.bbci.co.uk/news/technology/rss.xml",
		"http://feeds.bbci.co.uk/news/business/rss.xml"
	]
	
	You can use Postman Software or CURL command (Linux) to send the POST Message.

	You must be sure the links receives at least two entries. The response is an ID by each requirement

	JSon Response Example:

	[
		"f0137acc-4a38-45eb-b4b5-a35a1594f810"
	]
	
	each requirement is saved and its analysis is available to use in the follow step.
	
3) Query of news tendencies ordered by Tags Top 3
	
	This endpoind exposes a REST Web service context that shows all the top three trending topics ordered by tag and his own news in JSon Format.
	
	How it works:
	
	It works using the generated id and sent it via GET
	
	Get Example:
	
	http://localhost:8080/frequency/f0137acc-4a38-45eb-b4b5-a35a1594f810
	
	You can use whatever browser (Chrome, Mozilla, Internet Explorer, Microsoft Edge) or Postman Software or CURL command (Linux) to send the GET Message.
	
	Json Response list the top three tags around all the feed analysed, its frequency and all the news related to:
	
	JSon Response Example
	
	[
		{
			"frequency": 104,
			"tag": "coronavirus",
			"listNews": [
				{
					"title": "Coronavirus: Eurozone economy shrinks at record rate",
					"link": "https://www.bbc.co.uk/news/business-52487343"
				},
				{
					"title": "Coronavirus: Trump says China wants him to lose re-election",
					"link": "https://www.bbc.co.uk/news/world-us-canada-52482109"
				},
				.
				.
				.
				{
					"title": "Coronavirus: How a plus-size fashion retailer is adapting",
					"link": "https://www.bbc.co.uk/news/in-pictures-52374310"
				}
			],
			"idTag": "e62b1ef7-c1fa-4786-805c-e34aa16ea699"
		},
		{
			"frequency": 22,
			"tag": "lockdown",
			"listNews": [
				{
					"title": "Coronavirus: France offers subsidy to tempt lockdown cyclists",
					"link": "https://www.bbc.co.uk/news/world-europe-52483684"
				},
				{
					"title": "Coronavirus lockdown: Sierra Leone 'role model' minister carries baby and holds Zoom meeting",
					"link": "https://www.bbc.co.uk/news/world-africa-52487213"
				},
				.
				.
				.
				{
					"title": "Coronavirus: Businesses adapting to life under lockdown",
					"link": "https://www.bbc.co.uk/news/business-52321761"
				}
			],
			"idTag": "a483f115-c7e4-4387-9ab0-e98b7916c021"
		},
		{
			"frequency": 12,
			"tag": "end",
			"listNews": [
				{
					"title": "A Mixed Virus Bag in Iran for Persecuted Prisoners: 'I Was Forced to Endure all Kinds of Torture'",
					"link": "http://www.cbn.com/api/urlredirect.aspx?u=http://www1.cbn.com/cbnnews/cwn/2020/april/a-mixed-virus-bag-in-iran-for-persecuted-prisoners-i-was-forced-to-endure-all-kinds-of-torture"
				},
				{
					"title": "COVID-19 'Prayerdemic' Webcast to Air Friday: 'Gather All of Your Ministry Friends'",
					"link": "http://www.cbn.com/api/urlredirect.aspx?u=http://www1.cbn.com/cbnnews/cwn/2020/april/covid-19-prayerdemic-webcast-to-air-friday-gather-all-of-your-ministry-friends"
				},
				.
				.
				.
				{
					"title": "Coronavirus: Should maternity and paternity leave be extended?",
					"link": "https://www.bbc.co.uk/news/business-52421463"
				}
			],
			"idTag": "3e3ba18e-b10e-4a25-ad72-4b55b2d90d63"
		}
	]

4. Access to Embebbed DB

	You can use the persistence connecting with internal Spring-boot H2 tool
	
	How to connect to DB:
	
	Via Browser you can go to this URL:
	
	http://localhost:8080/h2-console/
	
	Using this connection parameters:
	
	* Driver Class: org.h2.Driver
	
	* JDBC URL: jdbc:h2:mem:testdb
	
	* User: sa
	
	* Password:
	
	And click on Connect.
	
	After that you can see all the tables and Run an SQL Statement.
	
	Example of SQL to list of news by a top trending in an specific id request:
	
	SELECT T.TITLE, T.URL NEWS T
	INNER JOIN REQUEST T1 ON T.REQ_ID=T1.REQ_ID
	INNER JOIN TAGS T2 ON T.NEWS_ID=T2.NEWS_ID
	WHERE T2.TAG='coronavirus' and t1.REQ_ID='f0137acc-4a38-45eb-b4b5-a35a1594f810'
	
5) That's it. Please read the Source. 

	Main Java Source is located in:

	com.hiring.exercise.software.cyan.MultiRSSAnalyzer.MultiRssAnalyzerApplication
	
	Controller Java Source is located in
	
	com.hiring.exercise.software.cyan.MultiRSSAnalyzer.controller.MainController


