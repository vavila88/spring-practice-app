package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    // predicate (lambda function) used to show how we can encapsulate a
    // function in a variable. declared static because it's used in a static
    // function
    public final static Predicate<String> filterBs = (n -> n.contains("b") ||
                                                    n.contains("s") ||
                                                    n.contains("B") ||
                                                    n.contains("S"));

    public static void main(String[] args) {
        // Launches this class as a web application with the bundled web server. Will
        // default to localhost:8080
        SpringApplication.run(Application.class, args);

        // unrelated code used to learn different aspects of functional programming in J8

        testStreams();

        // Lazy invocation example.
        lazyInvocation();

        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // print elements of a list in the old way, the new old way, and funcitonally
        exPrintNames(friends);

        // capitalize all letters in a collection. This requires creating a new collection for the modified elements
        exCapNames(friends);

        // pick names from the list based on some criteria
        exPickNames(friends);

        // pick names using terse lexical scoping and refactored lexical scoping
        exPickNamesScoped(friends);
    }

    private static void exPickNamesScoped(List<String> friends) {

        System.out.println("Filtering names that contain B's or S's using lexical scoping");

        long numB = friends.stream()
                            .filter(hasLetter("B"))
                            .map(Application::printLength)
                            .count();
        System.out.printf("input has %d entries with %s in it\n", numB, "B");

        long numS = friends.stream()
                            .filter(hasLetter("S"))
                            .map(Application::printLength)
                            .count();
        System.out.printf("input has %d entries with %s in it\n", numS, "S");

        // Now we do the same thing except we remove the static code smell and use a local function
        // using the Function class
        final Function<String, Predicate<String>> hasCharLambda =
                letter -> name -> name.contains(letter);
        /*
        a more verbose way of doing the above; this time we explicitly declare the types for letter and name
        final Function<String, Predicate<String>> hasCharLambda =
                (String letter) -> (String name) -> name.contains(letter);

        an even more verbose way of doing the above, in this case we explicitly encapsulate the scope of the nested
        predicate
        final Function<String, Predicate<String>> hasCharLambda =
                (String letter) -> {
                    Predicate<String> checkPredicate =
                        (String name) -> name.contains(letter);
                    return checkPredicate(letter);
                }
         */

        System.out.println("Perform the same thing but using the ");
        long numB2 = friends.stream()
                .filter(hasCharLambda.apply("B"))
                .map(Application::printLength)
                .count();
        System.out.printf("input has %d entries with %s in it\n", numB, "B");

        long numS2 = friends.stream()
                .filter(hasCharLambda.apply("S"))
                .map(Application::printLength)
                .count();
        System.out.printf("input has %d entries with %s in it\n", numS, "S");

    }

    /**
     * Pick names from the input list based on a filter;
     *      - only pick names with b's or s's in them
     *      -
     * @param friends
     */
    public static void exPickNames(List<String> friends) {

        System.out.println("Functionally filtering names that contain B's or S's (ignoring case)");
        // Just do it the functional way, I think I get the gist of it now.
        List<String> bsNames =
                friends.stream()
                       .filter(filterBs)
                        .map(String::toUpperCase)
                        .map(Application::printLength)
                        .collect(Collectors.toList());
    }

    /**
     * function used to capitalize the strings in the input list both imperatively (old-style) and
     * declaratively (functional) using method references
     * @param friends
     */
    public static void exCapNames(List<String> friends) {
        // Old way
        System.out.println("Capitalize contents of input with enhanced for-loop");
        final List<String> oldCaps = new ArrayList<>();
        for (String n : friends) {
            oldCaps.add(n.toUpperCase());
        }

        oldCaps.stream().forEach(System.out::println);

        // functional way
        System.out.println("Capitalize contents of input with functional programming");
        final List<String> funcCaps = friends.stream()
                                                .map(String::toUpperCase)
                                                .map(Application::printLength)
                                                .collect(Collectors.toList());

        funcCaps.stream().forEach(System.out::println);
    }

    /**
     * function used to practice the difference between using old-school declarative accessing of
     * the input list vs using functional programming with the .forEach() method.
     * @param friends
     */
    public static void exPrintNames(List<String> friends) {

        System.out.println("Printing names using an enhanced for-loop");
        // old way:
        for (String n : friends) {
            System.out.println(n);
        }

        System.out.println("Printing names using an functional programming with map");

        long printCnt = friends.stream()
                                .map(n -> {System.out.println(n); return n;})
                                .count();

        System.out.println("Printing names using an functional programming with forEach");
        friends.stream().forEach(n -> System.out.println(n));

        System.out.println("Printing names using an functional programming with method reference to println");
        friends.stream().forEach(System.out::println);
    }

    /**
     * function used to practice lazy invocation of intermediate operations
     */
    public static void lazyInvocation() {
        List<String> list = Arrays.asList("abc1", "abc2d", "abc3");

        // Due to lazy invocation, the functions below WILL NOT be called for EVERY element in the stream.
        // All intermediate steps are performed in series so long as they evaluate to true, up to and including the
        // terminal operation.
        Optional<String> stream = list.stream()
                // will be callled for the first two elements in the stream due to the type of terminal op specified
                .filter(element -> {
                    System.out.println("filter() was called");
                    return element.contains("2");
                })
                // Will only be called once due to the terminal op specified
                .map(element -> {
                    System.out.println("map() was called");
                    return element.toUpperCase();
                })
                // Since this op only wants the first elements that meets all the intermediate operations specified above,
                // it will not be called for any elements following the first one to meet all criteria
                .findFirst();
    }

    /**
     * Method used to test different aspects of functional programming using streams.
     */
    public static void testStreams() {
        // simple list to stream from
        List<String> list = Arrays.asList("abc1", "abc2d", "abc3");

        // Use the stream interface to retrieve the desired data. In this case we're looking for a count of elements
        // that meet the specified criteria;
        //      Don't consider the first element - skip()
        //      Only consider elements whose length is less than 5 - filter()
        //      Modify each element thus far in a certain manner - map()
        //      Further modify the data we have so far - sorted()
        //      Apply a terminal operation to get the desired data - count()
        long size = list.stream()
                .skip(1)
                .filter(element -> element.length() < 5)
                .map(element -> element.substring(0, 3))
                .count();
        System.out.println(size);
    }

    /**
     * practiceMethod - A practice static method used when learning functional programming in Java. Prints the length
     *                  of the input string. Accessed via method referencing
     * @param n - input string to check length on
     * @return the same input string
     */
    public static String printLength(final String n) {
        System.out.printf("Length of %s - %d\n", n, n.length());
        return n;
    }

    /**
     * predicate to check if a string contains letter. This pattern is preferred over calling
     * filter with the same lambda that only changes a single letter; decrease raw character count.
     *
     * This pattern still has issues, namely, we create a static method for this single purpose.
     * @param letter
     * @return
     */
    public static Predicate<String> hasLetter(String letter) {
        return name -> name.contains(letter);
    }
}
