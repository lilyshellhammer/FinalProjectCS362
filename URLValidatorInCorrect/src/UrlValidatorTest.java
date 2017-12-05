/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.Arrays;

import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   
   public void testManualTest()
   {
	   System.out.println("These should all evaluate to true: ");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("http://www.amazon.com is: ");
	   System.out.println(urlVal.isValid("http://www.amazon.com"));
	   

	   System.out.println("\n http://www.amazon.de is: ");
	   System.out.println(urlVal.isValid("http://www.amazon.de"));
	   
	   /*HERE IS AN ERROR THSI SHOULD BE TRUE BUT ISNT*/
	   System.out.println("\n h3t://go.cc:65636/t123 is: ");
	   System.out.println(urlVal.isValid("h3t://go.cc:65636/t123")); 
	  /*Sooo issue isn't with go.cc or t123*/
	   System.out.println("**Issue isn't with ht3, go.cc, or t123. h3t://go.cc/t123 is:");
	   System.out.println(urlVal.isValid("h3t://go.cc/t123")); 
	   /* ******* Error is with :65636   */
	   System.out.println("**Error is with :65636. http://go.cc:65636/t123 is:");
	   System.out.println(urlVal.isValid("http://go.cc:65636/t123")); 
	   
	   /* */
	   System.out.println("\n ftp://255.com/test1/ is: ");
	   System.out.println(urlVal.isValid("ftp://255.com/test1/"));

	   System.out.println("\n http://255.255.255.255:0/t123 is: ");
	   System.out.println(urlVal.isValid("http://255.255.255.255:0/t123"));

	   System.out.println("\n http://www.google.com:80/test1 is:");
	   System.out.println(urlVal.isValid("http://www.google.com:80/test1"));
	   
	   /*ERROR HERE AS WELL*/
	   System.out.println("\n http://www.google.com:80/test1?action=view is:");
	   System.out.println(urlVal.isValid("http://www.google.com:80/test1?action=view"));
	   System.out.println("** ?action=view should evaluate to true");
	   
	   System.out.println("\n file:///etc/hosts is");
	   System.out.println(urlVal.isValid("file:///etc/hosts"));
	   
	   /*ERROR HERE AS WELL */
	   System.out.println("\n http://localhost/test/index.html is:");
	   System.out.println(urlVal.isValid("http://localhost/test/index.html"));
	   /* *******Error is with "localhost"*/;
	   System.out.println("**If we change localhost to go.cc its works: http://go.cc/test/index.html is:");
	   System.out.println(urlVal.isValid("http://go.cc/test/index.html"));
		   
	   System.out.println("\n Now for the incorrect urls (should return false):");
	   /*The next ones are is incorrect, so printout NOT answer, aka should all
	    * still produce true values  */
	   System.out.println("http:/localhost/test/index.html is:");
	   System.out.println(urlVal.isValid("http:/localhost/test/index.html"));

	   System.out.println("\n file:///C:\\\\\\\\some.file is:");
	   System.out.println(urlVal.isValid("file:///C:\\\\some.file"));

	   System.out.println("\n file:///C:\\\\\\\\some.file is:");
	   System.out.println(urlVal.isValid("file:///C:\\\\\\\\some.file"));

	   System.out.println("\n http://256.256.256.256:0/t123 is: ");
	   System.out.println(urlVal.isValid("http://256.256.256.256:0/t123"));
	   System.out.println("** 256.256.256.256 should evaluate to false");
	   
	   
	   System.out.println("\n");
	   System.out.println("Summary: ");
	   System.out.println("Issue 1: Error with :65636 when that is a legal expension");
	   System.out.println("Issue 2: *Error is with \"localhost\" ");
	   System.out.println("Issue 3: ?action=view is a valid url ending, evaluates to false");
	   System.out.println("Issue 4: 256.256.256.256 is not a valid urlAuthority");
	   
   }
   
 
   /*Test Schemes*/
   public void testYourFirstPartition()
   {
	   System.out.println("\nScheme Partition");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("Test scheme and www.google.com :");
	   String[] schemes = getSchemes();
	   String urlToTest;
	   boolean testReturn;
	   for(int i = 0; i < schemes.length; i++) {
		   urlToTest = schemes[i] + "www.google.com";
		   testReturn = urlVal.isValid(urlToTest);
		   if(testReturn == true) {
			   System.out.println ("Passed: " + urlToTest );
		   }
		   else {
			   System.out.println ("Failed: " + urlToTest );
		   }
	   }
   }
   /*Test Authorities*/
   public void testYourSecondPartition(){
	   System.out.println("\nAuthority Partition");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("https:// and authority:");
	   String[] authorities = getAuthorities();
	   String urlToTest;
	   boolean testReturn;
	   for(int i = 0; i < authorities.length; i++) {
		   urlToTest = "https://" + authorities[i];
		   testReturn = urlVal.isValid(urlToTest);
		   if(testReturn == true) {
			   System.out.println ("Passed: " + urlToTest );
		   }
		   else {
			   System.out.println ("Failed: " + urlToTest );
		   }
	   }
   }
   /*Test Ports*/
   public void testYourThirdPartition(){
	   System.out.println("\nPorts Partition");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("https://www.google.com and port and ");
	   String[] ports = getPorts();
	   String urlToTest;
	   boolean testReturn;
	   for(int i = 0; i < ports.length; i++) {
		   urlToTest = "https://www.google.com" + ports[i];
		   testReturn = urlVal.isValid(urlToTest);
		   if(testReturn == true) {
			   System.out.println ("Passed: " + urlToTest );
		   }
		   else {
			   System.out.println ("Failed: " + urlToTest );
		   }
	   }
   }
   /*Test Paths*/
   public void testYourFourthPartition(){
	   System.out.println("\nPath Partition");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("https://www.google.com and path");
	   String[] paths = getPath();
	   String urlToTest;
	   boolean testReturn;
	   for(int i = 0; i < paths.length; i++) {
		   urlToTest = "https://www.google.com" + paths[i];
		   testReturn = urlVal.isValid(urlToTest);
		   if(testReturn == true) {
			   System.out.println ("Passed: " + urlToTest );
		   }
		   else {
			   System.out.println ("Failed: " + urlToTest );
		   }
	   }
   }
   /*Test Queries*/
   public void testYourFifthPartition(){
	   System.out.println("\nQuery Partition");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println("https://www.google.com and query:");
	   String[] queries = getQueries();
	   String urlToTest;
	   boolean testReturn;
	   for(int i = 0; i < queries.length; i++) {
		   urlToTest = "https://www.google.com/test" + queries[i];
		   testReturn = urlVal.isValid(urlToTest);
		   if(testReturn == true) {
			   System.out.println ("Passed: " + urlToTest );
		   }
		   else {
			   System.out.println ("Failed: " + urlToTest );
		   }
	   }
   }
   
   public String[] getSchemes() {
	   String[] schemes = {"https://", "https:/", "", "ht3://", "h3t://", 
			   "ftp://", "https//:", "http:/s"};
	   return schemes;
   }
   
   public String[] getAuthorities() {
	   String[] authorities = {"www.google.com", "www,google,com", "oregonstate.edu", 
			   "httpd.apache.org", "www.linux.org", "google.com", "google..com", 
			   "255.255.255.255","256.256.256.256","", ".." };
	   return authorities;
   }
   public String[] getPorts() {
	   String[] ports = {":80", ":-1", ":1", ":150", ":65636", ":-555", ":555", ""};
	   return ports;
   }
   
   public String[] getPath() {
	   String[] path = {"/home", "/home/", "./home", "/ho/me/", "//home", "/\\home", "/home/../", ""};
	   return path;
   }
   
   public String[] getQueries() {
	   String[] queries = {"?action=view", "?action=edit&mode=up", "", "?action=viewmode=edit", "action?=view", "?action=edit"};
	   return queries;
   }
   
   public void testIsValid()
   {
	   
	   System.out.println("\nUnit Testing");

	   int totalTests = 0;
	   int failedTests = 0;
	   boolean actual; //for testing the correctness of UrlValidator
	   
	   //get all parts of url
	   String[] schemes = getSchemes();
	   String[] authorities = getAuthorities();
	   String[] ports = getPorts();
	   String[] path = getPath();
	   String[] queries = getQueries();
	   
	   //make arrays for bad parts of URL
	   
	   String[] badSchemes = {"https:/", "ht3://", "https//:", "http:/s"};;			   
	   String[] badAuthorities = {"www,google,com", "httpd.apache.org", "google..com", "256.256.256.256", "", ".." };		   
	   String[] badPorts = {":-1", ":-555"}; 
	   String[] badPath = {"./home", "/ho/me/", "//home", "/\\home", "/home/../"};
	   String[] badQueries = {"?action=viewmode=edit", "action?=view", "?action=edit"};
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   int j = 0;
	   for(int i = 0;i<100000;i++)
	   {
		   /*Generate random URL*/
		   String urlToTest = "";
		   
		   
		   //get random scheme, authority, port, path, and query
		   int schemesIndex = (int) (Math.random() * schemes.length);
		   int authoritiesIndex = (int)(Math.random() * authorities.length);
		   int portsIndex = (int)(Math.random() * ports.length);
		   int pathIndex = (int)(Math.random() * path.length);
		   int queriesIndex = (int)(Math.random() * queries.length);

		   //set up URL to test
		   urlToTest = urlToTest + schemes[schemesIndex] + authorities[authoritiesIndex]
				   + ports[portsIndex] + path[pathIndex] + queries[queriesIndex]; 
		   
		   
		   /*Test the URL*/
		   boolean expected = true;
		   
		   //check if any part of the URL is false, if yes than expected is false
		   if (Arrays.asList(badSchemes).contains(schemes[schemesIndex]))
			   expected = false;
		   if (Arrays.asList(badAuthorities).contains(authorities[authoritiesIndex]))
			   expected = false;
		   if (Arrays.asList(badPorts).contains(ports[portsIndex]))
			   expected = false;
		   if (Arrays.asList(badPath).contains(path[pathIndex]))
			   expected = false;
		   if (Arrays.asList(badQueries).contains(queries[queriesIndex]))
			   expected = false;
		   
		   //test the url
		   actual = urlVal.isValid(urlToTest);
		   
		   // see if expected and actual match up
		   //if no, add to total failed and print out URL
		   if (expected != actual) {
			   failedTests++; //add one to failed counter
			   System.out.println("Url: " + urlToTest + " Expected: " + expected + " Actual: " + actual);
		   }   
		   //if yes, move on
		   
		   //add to total tests run
		   totalTests++;
	   }
	   
	   System.out.println("Total tests: " + totalTests + " Failed tests: " + failedTests);
   }
   
   public void testAnyOtherUnitTest()
   {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}
