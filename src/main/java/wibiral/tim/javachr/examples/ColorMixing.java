package wibiral.tim.javachr.examples;

import wibiral.tim.javachr.ConstraintHandler;
import wibiral.tim.javachr.SimpleHandler;
import wibiral.tim.javachr.constraints.Constraint;
import wibiral.tim.javachr.rules.Rule;
import wibiral.tim.javachr.rules.Simpagation;
import wibiral.tim.javachr.rules.Simplification;
import wibiral.tim.javachr.tracing.CommandLineTracer;

import java.util.ArrayList;
import java.util.List;

/**
 * CHR program that gives you the result of mixing some colors.
 * Inspired by Thom Frühwirth's example at http://www.chr.informatik.uni-ulm.de/~webchr/.
 * @see <a href="http://chr.informatik.uni-ulm.de/~webchr/">interaktive chr website</a>
 */
public class ColorMixing {
    public enum color {
        red, blue, yellow, green, purple, orange, brown
    }

    public static void main(String[] args) {
        ConstraintHandler colorHandler = new SimpleHandler(getRules());
//        colorHandler.setTrace(new CommandLineTracer()); // Use to see the process of the rule application

        color redAndBlue = (color) colorHandler.solve(color.red, color.blue).get(0).value();
        System.out.println("red and blue is " + redAndBlue.toString());

        color redAndYellow = (color) colorHandler.solve(color.red, color.yellow).get(0).value();
        System.out.println("red and yellow is " + redAndYellow.toString());

        color greenAndGreen = (color) colorHandler.solve(color.green, color.green).get(0).value();
        System.out.println("green and green is " + greenAndGreen.toString());

        color orangeAndBrown = (color) colorHandler.solve(color.orange, color.brown).get(0).value();
        System.out.println("orange and brown is " + orangeAndBrown.toString());
    }

    static Rule[] getRules(){
        List<Rule> rules = new ArrayList<>();
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.red) && x[1].equals(color.blue))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.purple))));

        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.blue) && x[1].equals(color.yellow))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.green))));

        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.yellow) && x[1].equals(color.red))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.orange))));


        // Version 1: Just Simplification
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.blue))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.red))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.yellow))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.purple))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.green))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.orange))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));
        rules.add(new Simplification(2)
                .guard(x -> x[0].equals(color.brown) && x[1].equals(color.brown))
                .body((oldC, newC) -> newC.add(new Constraint<>(color.brown))));


//        // Version 2: Simpagation instead of Simplification:
//        rules.add(new Simpagation(1, 1).guard((head1, head2) -> head1[0].equals(color.brown))
//        // .body((head1, head2, newC) -> {  })   // Body is not necessary
//        );

        // Additional:
        // remove duplicates:
        rules.add(new Simpagation(1, 1).guard((head1, head2) -> head1[0].equals(head2[0])));

        return rules.toArray(new Rule[0]);
    }
}
