##Excercise-8
To test the randomMove() method the idea should be that given adjacent accesible squares
a list of all possible directions should be computed and from that list a direction should
be chosen at random.Since the output can be different(random) even if the input supplied is the same
therefore we provide the randomMove() method with "seeds".

##Excercise-10
Code coverage is a structural testing technique in which different metrics(such as line,statement,branch,condition etc.)
are employed to check what percentage of our code is "covered" with respectnto that metric.Code coverage provides information
about what to focus at while structural testing.Its advantage is that it can be useful in finding and eliminating bugs in the
code and increasing the quality of the product.

100% code coverage with regard to a specific metric,for example-line coverage implies that all lines of the code under test are executed at least once.It means that
the behaviour of those lines is tested but that does not mean that the class is not completely tested since code coverage does not
focus on specification of the code.

However,code coverage is not the answer to the problem of software testing and 100% code coverage is not a guarantee of 100% bug free software.You can infer a certain level
of quality in your tests based on their coverage, but you still need to be writing meaningful tests.

##Excercise-12
Unit tests are relatively faster than system and integraation tests.One thing that can slow them down is the sheer size of the component/unit they are testing.
The more complicated the unit,the more its dependency on other units,the longer it takes with the time difference being more pronounced as we move up the testing pyramid.
Also the more time consuming the functions the class performs are directly affects time required to test,so classes with less complicated methods are tested quicker.Not mocking the tests lso makes them slower.
One way to speed up our tests.Having more unit tests than tests of types that occur higher in the testing pyramid ensures that tests are fast.Also using
mock object is quicker than actually making use of different components that feed data to our class under test.
