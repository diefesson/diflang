package com.diefesson.difcomp.rustlike;

import com.diefesson.difcomp.grammar.Grammar;
import static com.diefesson.difcomp.rustlike.RLVariables.*;

import com.diefesson.difcomp.error.GrammarException;

import static com.diefesson.difcomp.rustlike.RLTokens.*;

public class RLGrammar {

    private RLGrammar() {
    }

    public static Grammar rlGrammar() throws GrammarException {
        return Grammar.builder()
                .rule(PROGRAM, FUNCTIONS)
                // Types
                .rule(TYPE, TYPE_BOOL)
                .rule(TYPE, TYPE_CHAR)
                .rule(TYPE, TYPE_F32)
                .rule(TYPE, TYPE_S32)
                .rule(TYPE, TYPE_STRING)
                .rule(TYPE, TYPE_UNIT)
                // Constants
                .rule(CONST, CONST_BOOL)
                .rule(CONST, CONST_CHAR)
                .rule(CONST, CONST_F32)
                .rule(CONST, CONST_S32)
                .rule(CONST, CONST_STRING)
                // Operator precendence
                .rule(EXPR, OR_EXPR)
                // Or
                .rule(OR_EXPR, OR_EXPR, OP_OR, AND_EXPR)
                .rule(OR_EXPR, AND_EXPR)
                // And
                .rule(AND_EXPR, AND_EXPR, OP_AND, NOT_EXPR)
                .rule(AND_EXPR, NOT_EXPR)
                // Not
                .rule(NOT_EXPR, OP_NOT, REL_EXPR)
                .rule(NOT_EXPR, REL_EXPR)
                // Relationals
                .rule(REL_EXPR, SUM_EXPR, OP_EQ, SUM_EXPR)
                .rule(REL_EXPR, SUM_EXPR, OP_LE, SUM_EXPR)
                .rule(REL_EXPR, SUM_EXPR, OP_GE, SUM_EXPR)
                .rule(REL_EXPR, SUM_EXPR, OP_LT, SUM_EXPR)
                .rule(REL_EXPR, SUM_EXPR, OP_GT, SUM_EXPR)
                .rule(REL_EXPR, SUM_EXPR)
                // Sum equivalents
                .rule(SUM_EXPR, SUM_EXPR, OP_ADD, MUL_EXPR)
                .rule(SUM_EXPR, SUM_EXPR, OP_SUB, MUL_EXPR)
                .rule(SUM_EXPR, MUL_EXPR)
                // Mul equivalents
                .rule(MUL_EXPR, MUL_EXPR, OP_MUL, CONV_EXPR)
                .rule(MUL_EXPR, MUL_EXPR, OP_DIV, CONV_EXPR)
                .rule(MUL_EXPR, CONV_EXPR)
                // Conversion
                .rule(CONV_EXPR, LEAF_EXPR, KW_AS, TYPE)
                .rule(CONV_EXPR, LEAF_EXPR)
                // Leaf expressions
                .rule(CONST_EXPR, CONST)
                .rule(ID_EXPR, ID)
                .rule(SUB_EXPR, PUNC_ARG_OPEN, EXPR, PUNC_ARG_CLOSE)
                .rule(CALL_EXPR, ID, CALL_ARGS)
                .rule(LEAF_EXPR, CONST_EXPR)
                .rule(LEAF_EXPR, ID_EXPR)
                .rule(LEAF_EXPR, SUB_EXPR)
                .rule(LEAF_EXPR, CALL_EXPR)
                // Conditional statements
                .rule(IF_STMNT, KW_IF, EXPR, BLOCK)
                .rule(ELIF_STMNT, KW_ELIF, EXPR, BLOCK)
                .rule(ELSE_STMNT, KW_ELSE, BLOCK)
                .rule(ELIF_STMNTS, ELIF_STMNTS, ELIF_STMNT)
                .rule(ELIF_STMNTS, ELIF_STMNT)
                .rule(COND_STMNT, IF_STMNT)
                .rule(COND_STMNT, IF_STMNT, ELIF_STMNTS)
                .rule(COND_STMNT, IF_STMNT, ELIF_STMNTS, ELSE_STMNT)
                // Other statements
                .rule(NORMAL_STMNT, TYPE, ID, PUNC_STMNT_END)
                .rule(NORMAL_STMNT, TYPE, ID, PUNC_DEFINITION, EXPR, PUNC_STMNT_END)
                .rule(NORMAL_STMNT, ID, PUNC_DEFINITION, EXPR, PUNC_STMNT_END)
                .rule(NORMAL_STMNT, EXPR, PUNC_STMNT_END)
                .rule(RETURN_STMNT, KW_RETURN, EXPR, PUNC_STMNT_END)
                .rule(STMNT, NORMAL_STMNT)
                .rule(STMNT, RETURN_STMNT)
                .rule(STMNT, COND_STMNT)
                .rule(STMNTS, STMNTS, STMNT)
                .rule(STMNTS, STMNT)
                .rule(BLOCK, PUNC_BLOCK_OPEN, STMNTS, PUNC_BLOCK_CLOSE)
                .rule(BLOCK, PUNC_BLOCK_OPEN, PUNC_BLOCK_CLOSE)
                // Functions
                .rule(ARGS, ARGS, PUNC_COMMA, EXPR)
                .rule(ARGS, EXPR)
                .rule(PARAM, TYPE, ID)
                .rule(PARAMS, PARAMS, PUNC_COMMA, PARAM)
                .rule(PARAMS, PARAM)
                .rule(CALL_ARGS, PUNC_ARG_OPEN, ARGS, PUNC_ARG_CLOSE)
                .rule(CALL_ARGS, PUNC_ARG_OPEN, PUNC_ARG_CLOSE)
                .rule(FUNCTION_PARAMS, PUNC_ARG_OPEN, PARAMS, PUNC_ARG_CLOSE)
                .rule(FUNCTION_PARAMS, PUNC_ARG_OPEN, PUNC_ARG_CLOSE)
                .rule(FUNCTION_HEADER, KW_FUN, ID, FUNCTION_PARAMS, PUNC_TWO_DOTS, TYPE)
                .rule(FUNCTION, FUNCTION_HEADER, BLOCK)
                .rule(FUNCTIONS, FUNCTIONS, FUNCTION)
                .rule(FUNCTIONS, FUNCTION)
                .build();
    }
}
