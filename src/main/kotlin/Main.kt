package com.example

import java.util.Stack
import kotlin.math.pow

fun main() {
    print("Input (prefix):\t")

    val rpn = formRPN(readln())
    print("Output (RPN):\t")
    rpn.forEach { lex -> print("$lex ") }
    println()

    print("Result: ${calcRPN(rpn.toStack())}")
    println()
}

fun formRPN(inputSequence: String): List<String> {
    var lexemes = inputSequence.split(" ")
    var rpn = mutableListOf<String>()

    var st = Stack<String>()
    for (item in lexemes) {
        if (item.toIntOrNull() != null) {
            rpn.add(item)
        } else if (item == "(") {
            st.push(item)
        } else if (item == ")") {
            if (st.empty()) {
                print("Error! wrong sequence!")
                return listOf()
            }

            while (!st.empty() && (st.top() != "(")) {
                rpn.add(st.pop())
            }
            if (st.empty()) {
                print("Error! wrong sequence!")
                return listOf()
            } else {
                st.pop()
            }
        } else {
            while (!st.empty() && !lowerPri(st.top(), item) && st.top() != "(") {
                rpn.add(st.pop())
            }
            if (st.empty() || lowerPri(st.top(), item)) {
                st.push(item)
            } else {
                st.pop()
            }
        }
    }

    while (!st.empty())
        rpn.add(st.pop())

    return rpn
}

fun calcRPN(rpn: Stack<String>): Int {
    if (rpn.empty()) return 0

    if (rpn.top().toIntOrNull() != null) {
        return rpn.pop().toInt()
    }

    val op = rpn.pop()
    return makeOp(calcRPN(rpn), op, calcRPN(rpn))
}

fun makeOp(
    op1: Int,
    op: String,
    op2: Int,
): Int {
    return when (op) {
        "+" -> {
            op2 + op1
        }
        "-" -> {
            op2 - op1
        }
        "*" -> {
            op2 * op1
        }
        "/" -> {
            op2 / op1
        }
        "^" -> {
            op2.toDouble().pow(op1).toInt()
        }
        else -> 0xDEAD // 57005
    }
}

fun lowerPri(
    op1: String,
    op2: String,
): Boolean {
    val op = arrayOf("+", "-", "*", "/", "^")
    val i1 = op.indexOf(op1)
    val i2 = op.indexOf(op2)

    return i1 < i2
}

fun Stack<String>.top(): String {
    val a = this.pop()
    this.push(a)
    return a
}

fun List<String>.toStack(): Stack<String> {
    val st = Stack<String>()
    this.forEach { lex -> st.add(lex) }

    return st
}
