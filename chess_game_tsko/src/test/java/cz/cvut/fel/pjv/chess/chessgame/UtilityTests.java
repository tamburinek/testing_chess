package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.Utility;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilityTests {

    @ParameterizedTest(name = "Test if {0} is converted to {1}")
    @CsvSource({"0,a", "1,b", "2, c", "3,d", "4,e","5,f", "6,g","7,h"})
    public void utilityTest_xToLetterTest(int x, String result){
        //arrange
        Utility helper = new Utility();
        int xCoordinate = x;
        String expectedResult = result;

        //act
        String realResult = helper.xToLetter(xCoordinate);

        //assert
        assertEquals(expectedResult,realResult);
    }

    @ParameterizedTest(name = "Test if {0} is converted to {1}")
    @CsvSource({"0,8", "1,7", "2, 6", "3,5", "4,4","5,3", "6,2","7,1"})
    public void utilityTest_yInvertTest(int y, int result){
        //arrange
        Utility helper = new Utility();
        int yCoordinate = y;
        int expectedResult = result;

        //act
        int realResult = helper.yInvert(yCoordinate);

        //assert
        assertEquals(expectedResult,realResult);
    }
}
