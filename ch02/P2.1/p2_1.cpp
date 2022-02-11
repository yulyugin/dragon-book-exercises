/* Copyright (C) 2022 Evgenii Iuliugin
 *
 * Implement a translation from integers to roman numerals based on the
 * syntax-directed translation scheme developed in Exercise 2.9.
 *
 * Syntax-directed translation scheme:
 * ones -> 0
 * ones -> 1 {print('I')}
 * ones -> 2 {print('II')}
 * ones -> 3 {print('III')}
 * ones -> 4 {print('IV')}
 * ones -> 5 {print('V')}
 * ones -> 6 {print('VI')}
 * ones -> 7 {print('VII')}
 * ones -> 8 {print('VIII')}
 * ones -> 9 {print('IX')}
 * tens -> 0
 * tens -> 1 {print('X')}
 * tens -> 2 {print('XX')}
 * tens -> 3 {print('XXX')}
 * tens -> 4 {print('XL')}
 * tens -> 5 {print('L')}
 * tens -> 6 {print('LX')}
 * tens -> 7 {print('LXX')}
 * tens -> 8 {print('LXXX')}
 * tens -> 9 {print('XC')}
 * hundreds -> 0
 * hundreds -> 1 {print('C')}
 * hundreds -> 2 {print('CC')}
 * hundreds -> 3 {print('CCC')}
 * hundreds -> 4 {print('CD')}
 * hundreds -> 5 {print('D')}
 * hundreds -> 6 {print('DC')}
 * hundreds -> 7 {print('DCC')}
 * hundreds -> 8 {print('DCCC')}
 * hundreds -> 9 {print('CM')}
 * thousands -> e
 * thousands -> 1 {print('M')}
 * thousands -> 2 {print('MM')}
 * thousands -> 3 {print('MMM')}
 * int -> thousands hundreds tens ones | tens ones | ones
 */

#include <gtest/gtest.h>

class UnsupportedSymbol : public std::exception {};
class SizeError : public std::exception {};

int
CharToInt(const char c)
{
        int ret = c - '0';
        if (ret < 0 || ret > 9)
                throw UnsupportedSymbol();
        return ret;
}

std::string
Ones(const char digit)
{
        switch (CharToInt(digit)) {
        case 0:
                return "";
        case 1:
                return "I";
        case 2:
                return "II";
        case 3:
                return "III";
        case 4:
                return "IV";
        case 5:
                return "V";
        case 6:
                return "VI";
        case 7:
                return "VII";
        case 8:
                return "VIII";
        case 9:
                return "IX";
        default:
                assert(0);
        }
}

std::string
Tens(const char digit)
{
        switch (CharToInt(digit)) {
        case 0:
                return "";
        case 1:
                return "X";
        case 2:
                return "XX";
        case 3:
                return "XXX";
        case 4:
                return "XL";
        case 5:
                return "L";
        case 6:
                return "LX";
        case 7:
                return "LXX";
        case 8:
                return "LXXX";
        case 9:
                return "XC";
        default:
                assert(0);
        }
}

std::string
Hundreds(const char digit)
{
        switch (CharToInt(digit)) {
        case 0:
                return "";
        case 1:
                return "C";
        case 2:
                return "CC";
        case 3:
                return "CCC";
        case 4:
                return "CD";
        case 5:
                return "D";
        case 6:
                return "DC";
        case 7:
                return "DCC";
        case 8:
                return "DCCC";
        case 9:
                return "CM";
        default:
                assert(0);
        }
}

std::string
Thousands(const char digit)
{
        switch (CharToInt(digit)) {
        case 1:
                return "M";
        case 2:
                return "MM";
        case 3:
                return "MMM";
        default:
                throw SizeError();
        }
}

std::string
IntToRoman(std::string integer) {
        std::string ret = "";
        size_t size = integer.size();
        switch (size) {
        case 4:
                ret += Thousands(integer[size - 4]);
        case 3:
                ret += Hundreds(integer[size - 3]);
        case 2:
                ret += Tens(integer[size - 2]);
        case 1:
                ret += Ones(integer[size - 1]);
                break;
        default:
                throw SizeError();
        }
        return ret;
}

TEST(IntToRoman, testZero)
{
        EXPECT_EQ(IntToRoman("0"), "");
}

TEST(IntToRoman, testOne)
{
        EXPECT_EQ(IntToRoman("1"), "I");
}

TEST(IntToRoman, testTen)
{
        EXPECT_EQ(IntToRoman("10"), "X");
}

TEST(IntToRoman, testFiftyFive)
{
        EXPECT_EQ(IntToRoman("55"), "LV");
}

TEST(IntToRoman, testSixtyNine)
{
        EXPECT_EQ(IntToRoman("69"), "LXIX");
}

TEST(IntToRoman, testNinetyNine)
{
        EXPECT_EQ(IntToRoman("99"), "XCIX");
}

TEST(IntToRoman, testFortyThree)
{
        EXPECT_EQ(IntToRoman("43"), "XLIII");
}

TEST(IntToRoman, test126)
{
        EXPECT_EQ(IntToRoman("126"), "CXXVI");
}

TEST(IntToRoman, test666)
{
        EXPECT_EQ(IntToRoman("666"), "DCLXVI");
}

TEST(IntToRoman, test2022)
{
        EXPECT_EQ(IntToRoman("2022"), "MMXXII");
}

TEST(IntToRoman, test3999)
{
        EXPECT_EQ(IntToRoman("3999"), "MMMCMXCIX");
}

TEST(IntToRoman, testUnsupportedSymbol)
{
        EXPECT_THROW(IntToRoman("abcd"), UnsupportedSymbol);
}

TEST(IntToRoman, testSizeError)
{
        EXPECT_THROW(IntToRoman(""), SizeError);
        EXPECT_THROW(IntToRoman("12345"), SizeError);
}
