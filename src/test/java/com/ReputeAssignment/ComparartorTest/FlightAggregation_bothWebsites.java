package com.ReputeAssignment.ComparartorTest;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightAggregation_bothWebsites 
{
	public static void main(String[] args) throws InterruptedException, Throwable 
	{
		CSVWriter write = new CSVWriter(new FileWriter("Flightsdata\\writeFlightsData.csv"));   //CSVWriter class is used to write CSV data to Writer implementation
		String set[]= {"Flight operators","Price on cleartrip","price on Paytm"};
		write.writeNext(set);   //The writeNext method writes the array to the an individual line of the file.
		ArrayList<String> list1 = new ArrayList<String>();  //list1 have All ClearTrip AirlineNames and Numbers
		ArrayList<String> list2 = new ArrayList<String>();  //list2 have All ClearTripFlight_prices
		ArrayList<String> list3 = new ArrayList<String>();  //list3 have All PayTm AirlineNames and Numbers
		ArrayList<String> list4 = new ArrayList<String>();  ////list4 have All PayTm Flight_prices
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.cleartrip.com/flights");
		driver.findElement(By.xpath("//input[@placeholder='Where from?']")).sendKeys("Bangalore");
		driver.findElement(By.xpath("//p[contains(text(),'Bangalore')]")).click();
		driver.findElement(By.xpath("//input[@placeholder='Where to?']")).sendKeys("Delhi");
		driver.findElement(By.xpath("//p[contains(text(),'DEL')]")).click();

		//click
		driver.findElement(By.xpath("//button[@class='flex flex-middle  t-all fs-2 focus:bc-secondary-500 bg-transparent bc-neutral-100 c-pointer pr-2 pl-3 pt-2 pb-2 ba br-4 h-8 c-neutral-900']")).click();  //click on ClearTRIP Calender
		Thread.sleep(2000);
       
		try
		{
			driver.findElement(By.xpath("//div[@aria-label='Sat Aug 26 2023']//div[@class='p-1 day-gridContent fs-14 fw-500 flex flex-middle flex-column flex-center flex-top']")).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath("//div[@aria-label='Sat Aug 26 2023']//div[@class='p-1 day-gridContent fs-14 fw-500 flex flex-middle flex-column flex-center flex-top']")).click();
		}

		driver.findElement(By.xpath("//span[contains(@class,'fw-600 fs-4 lh-2 flex flex-middle')]")).click();  //click on search Flights

		driver.findElement(By.xpath("//p[normalize-space()='Non-stop']")).click();  //Check the Nonstop CheckBox
		Thread.sleep(1000);
		List<WebElement> flight_operator = driver.findElements(By.xpath("//p[@class='fw-500 fs-2 c-neutral-900']"));

		List<WebElement> flight_Numbers = driver.findElements(By.xpath("//p[@class='fs-1 c-neutral-400 pt-1']"));

		List<WebElement> price_details = driver.findElements(By.xpath("//p[@class='m-0 fs-5 fw-700 c-neutral-900 false']"));


		for (int i = 0; i < flight_Numbers.size(); i++) 
		{
			String flightoperatorNames = flight_operator.get(i).getText();
			String flightnumbers = flight_Numbers.get(i).getText();
			String clearTrip_prices = price_details.get(i).getText();
			String mergeAirlineNumAndName= flightoperatorNames.concat(flightnumbers);
			list1.add(mergeAirlineNumAndName);
			list2.add(clearTrip_prices);
		}


		
		//AggregationWebsite2
		driver.get("https://tickets.paytm.com/flights/");

		driver.findElement(By.xpath("//div[@class='_1P5EG']/div[@id='from']//span[@id='srcCode']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_1jNhs']//img")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_1jNhs']//input[@id='text-box']")).sendKeys("Bengaluru");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='lM769']//div[text()='Bengaluru']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_1P5EG']/div[@id='to']//span[@id='destCode']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_27rS9']/img[@class='_3i2Wp']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_2kEaD']/input[@id='text-box']")).sendKeys("Delhi");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='lM769']//div[text()='Delhi']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='_1xUOn']//span[text()='Departure Date']/following-sibling::span[@id='departureDate']")).click();
		Thread.sleep(1000);


		String month = "August";
		String year = "2023";
		String dateVal = "26";

		while(true)
		{
			String monYear = driver.findElement(By.xpath("(//td[@class='calendar__month _18o18'])[1]")).getText();
			String arr[]=monYear.split(" ");
			String mon=arr[0];
			String yy=arr[1];

			if(mon.equalsIgnoreCase(month) && yy.equals(year))
				break;
			else
				driver.findElement(By.xpath("//i[@class='gA7KZ _3nECU']")).click(); 
		}


		List<WebElement> alldates = driver.findElements(By.xpath("(//table)[1][@class='calendar']//tr//td//div"));
		for( WebElement date:alldates)
		{
			String dt= date.getText();
			if(dt.equals(dateVal))
			{
				date.click();
				break;
			}
		}

		driver.findElement(By.xpath("//button[@id='flightSearch']")).click();  //ClickOn Search Flight button in PayTM website


		//NextPage 
	Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='nonStop']//i[@class='ar-xN']")).click();

		 //driver.findElement(By.xpath("//div[@class='_1RmR_']//div[@id='nonStop']")).click();
		
		List<WebElement> payTmFlight_prices = driver.findElements(By.xpath("//div[@class='_2MkSl']"));  //Fetching All the Paytm Website Nonstop FlightPrices

		for(WebElement data:payTmFlight_prices )
		{

			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,500)");
			//System.out.println(data.getText());
			list4.add(data.getText());
		}
		List<WebElement> click = driver.findElements(By.xpath("//div[@class='_29dj3']"));  //Click On Flight details to get Airline Names And Numbers
		for(WebElement ele:click)
		{

			Thread.sleep(2000);
			ele.click();
			Thread.sleep(1000);
			String num = driver.findElement(By.xpath("//div[@class='_3tMEB']//span[2]")).getText();
			String name =driver.findElement(By.xpath("//div[@class='_3tMEB']//span[1] ")).getText();
			String nameAndNum = name.concat(num);
			list3.add(nameAndNum);
			//System.out.println(nameAndNum);
		}
		for(int j=0;j<list1.size();j++)
		{

			for(int i1=0;i1<list3.size();i1++)
			{
				//System.out.println(list3.get(i));
				if(list1.get(j).trim().equals(list3.get(i1).trim()))
				{
					String nameNum = list1.get(j).replace("₹", " ");//removing ₹
					 String price = list2.get(j).replace("₹", " ");;
					String price2 = list4.get(i1).replace("₹", " ");;
					System.out.print(list1.get(j)+" ");
					System.out.print(list2.get(j)+" ");

					System.out.println(list4.get(i1));
					String set1[]= {nameNum,price,price2};
				    write.writeNext(set1);
				}

			}
		}
		write.flush();

	}



}
