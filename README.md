# Nulogy-Pricing-Problem


Runtime Version: 1.8.0_121

To run tests from command line type:
java Main 'initial base price', number of people, materials

Important:
Enclose the initial base price between single quotes
Separate arguments using comma

Examples:
java Main '$100.00', 1 person, drugs, books
java Main '$200', 0 people
java Main '$5,332', 4 people, books, drugs, cute puppies, plants

Assumptions (see reflection.txt for justification):
- User will enter initial base cost (Error returned otherwise)
- User will enter number of people as 'x person(s)/people', where x is any integer (Error returned otherwise)

