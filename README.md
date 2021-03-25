# GO-LANG-float-literal-analyzer

**This program is written in Java. Take in string or character array value as input. Return true if the value matches the format of a floating point literal in GO, otherwise return false.**

*https://golang.org/ref/spec#Floating-point_literals*

    float_lit         = decimal_float_lit | hex_float_lit .

    decimal_float_lit = decimal_digits "." [ decimal_digits ] [ decimal_exponent ] |
                        decimal_digits decimal_exponent |
                        "." decimal_digits [ decimal_exponent ] .
    decimal_exponent  = ( "e" | "E" ) [ "+" | "-" ] decimal_digits .

    hex_float_lit     = "0" ( "x" | "X" ) hex_mantissa hex_exponent .
    hex_mantissa      = [ "_" ] hex_digits "." [ hex_digits ] |
                        [ "_" ] hex_digits |
                        "." hex_digits .
    hex_exponent      = ( "p" | "P" ) [ "+" | "-" ] decimal_digits .

    decimal_lit    = "0" | ( "1" … "9" ) [ [ "_" ] decimal_digits ] .
    hex_lit        = "0" ( "x" | "X" ) [ "_" ] hex_digits .

    decimal_digits = decimal_digit { [ "_" ] decimal_digit } .
    hex_digits     = hex_digit { [ "_" ] hex_digit } .
