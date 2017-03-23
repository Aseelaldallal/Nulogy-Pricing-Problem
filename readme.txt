
Runtime Version: 1.8.0_121


TO RUN JAVA FILES: 

To run tests from command line type:
java Main initial base price, number of people, materials

Examples:
java Main $100.00, 1 person, drugs, books
java Main $200, 0 people
java Main $5,332, 4 people, books, drugs, cute puppies, plants

TO RUN PREBUILD JAR FILE:

java -jar jar\nulogyPriceCalc.jar $1200, 0 people, Books, food

Note: You can also import the project into eclipse. 

Assumptions (see reflection.txt for justification):
- User will enter initial base cost (Error returned otherwise)
- User will enter number of people as 'x person(s)/people', where x is any integer (Error returned otherwise)
