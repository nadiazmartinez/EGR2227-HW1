import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class HtmlValidatorTest {

    //files used to test against
    private static final String EXPECTED_TEMPLATE = "expected_output/validate_result_for_test%d.txt";
    private static final String INPUT_TEMPLATE = "expected_output/test%d.html";


    //calls helper method testValidatorWithFiles
    //uses Expected template + input template for each individual test 1-8
    private static void testAgainstFiles(int testNumber) {
        testValidatorWithFiles(String.format(EXPECTED_TEMPLATE, testNumber), String.format(INPUT_TEMPLATE, testNumber));
    }

    //helper method
    //uses files to test input
    private static void testValidatorWithFiles(String expectedOutputFilePath, String validatorInputFilePath) {
        String rawInput = dumpFileContentsToString(validatorInputFilePath);
        String expected = dumpFileContentsToString(expectedOutputFilePath);
        HtmlValidator validator = new HtmlValidator(HtmlTag.tokenize(rawInput));

        String validatorOutput = captureValidatorOutput(validator);

        Assert.assertEquals("Validator output must match expected value", expected, validatorOutput);  //error message
    }

    //tests and returns String of HtmlValidator validator output
    private static String captureValidatorOutput(HtmlValidator validator) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        validator.validate();

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString();
    }

    //returns string of parameters or error message + null
    private static String dumpFileContentsToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath); //error message
            return null;
        }
    }

    @Test
    public void test1() {
        testAgainstFiles(1);
    }

    @Test
    public void test2() {
        testAgainstFiles(2);
    }

    @Test
    public void test3() {
        testAgainstFiles(3);
    }

    @Test
    public void test4() {
        testAgainstFiles(4);
    }

    @Test
    public void test5() {
        testAgainstFiles(5);
    }

    @Test
    public void test6() {
        testAgainstFiles(6);
    }

    @Test
    public void test7() {
        testAgainstFiles(7);
    }

    @Test
    public void test8() {
        testAgainstFiles(8);
    }

    //
    @Test
    public void addTagTest() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();

        tags.forEach(validator::addTag);

        // TODO: Write the expect assert statement for getting the correct tags.
        Assert.assertEquals(tags,validator.getTags());   //


    }


    @Test(expected = IllegalArgumentException.class)
    // TODO: write the method addNullTagTest to account null tags.

    //adds up all null tags
    public void addNullTagTest() {
        HtmlValidator validator = new HtmlValidator();
        validator.addTag(null);
    }


    @Test
    public void removeAllTest() { //given
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        // TODO: Incorporate the addition and removal of tags
        validator.addTag(new HtmlTag("General Kenobi"));  //adds tags

        validator.removeAll("General Kenobi"); //removes tags


        // TODO: Write the correct assert statement to test the tags
        Assert.assertEquals(tags, validator.getTags());   //test tags with validator tags

    }



    // TODO: Generate the correct test and method for removeAllNullTest
    @Test(expected = IllegalArgumentException.class)
    public void removeAllNullTest() {
        HtmlValidator validator = new HtmlValidator();

        validator.removeAll(null);  //removes all null elements
    }
}

