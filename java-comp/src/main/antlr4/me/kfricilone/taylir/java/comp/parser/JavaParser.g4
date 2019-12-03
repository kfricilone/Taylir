/*
 [The "BSD licence"]
 Copyright (c) 2013 Terence Parr, Sam Harwell
 Copyright (c) 2017 Ivan Kochurkin (upgrade to Java 8)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

parser grammar JavaParser;

options { tokenVocab=JavaLexer; }

compilationUnit
    : packageDeclaration? importDeclaration* typeDeclaration* EOF
    ;

packageDeclaration
    : annotation* PACKAGE qualifiedName SEMI
    ;

importDeclaration
    : IMPORT STATIC? qualifiedName (DOT MUL)? SEMI
    ;

typeDeclaration
    : classOrInterfaceModifier*
      (classDeclaration | enumDeclaration | interfaceDeclaration | annotationTypeDeclaration)
    | SEMI
    ;

modifier
    : classOrInterfaceModifier
    | NATIVE
    | SYNCHRONIZED
    | TRANSIENT
    | VOLATILE
    ;

classOrInterfaceModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | STATIC
    | ABSTRACT
    | FINAL    // FINAL for class only -- does not apply to interfaces
    | STRICTFP
    ;

variableModifier
    : FINAL
    | annotation
    ;

classDeclaration
    : CLASS IDENTIFIER typeParameters?
      (EXTENDS typeType)?
      (IMPLEMENTS typeList)?
      classBody
    ;

typeParameters
    : LT typeParameter (COMMA typeParameter)* GT
    ;

typeParameter
    : annotation* IDENTIFIER (EXTENDS typeBound)?
    ;

typeBound
    : typeType (BITAND typeType)*
    ;

enumDeclaration
    : ENUM IDENTIFIER (IMPLEMENTS typeList)? LBRACE enumConstants? COMMA? enumBodyDeclarations? RBRACE
    ;

enumConstants
    : enumConstant (COMMA enumConstant)*
    ;

enumConstant
    : annotation* IDENTIFIER arguments? classBody?
    ;

enumBodyDeclarations
    : SEMI classBodyDeclaration*
    ;

interfaceDeclaration
    : INTERFACE IDENTIFIER typeParameters? (EXTENDS typeList)? interfaceBody
    ;

classBody
    : LBRACE classBodyDeclaration* RBRACE
    ;

interfaceBody
    : LBRACE interfaceBodyDeclaration* RBRACE
    ;

classBodyDeclaration
    : SEMI
    | STATIC? block
    | modifier* memberDeclaration
    ;

memberDeclaration
    : methodDeclaration
    | genericMethodDeclaration
    | fieldDeclaration
    | constructorDeclaration
    | genericConstructorDeclaration
    | interfaceDeclaration
    | annotationTypeDeclaration
    | classDeclaration
    | enumDeclaration
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */
methodDeclaration
    : typeTypeOrVoid IDENTIFIER formalParameters (LBRACK RBRACK)*
      (THROWS qualifiedNameList)?
      methodBody
    ;

methodBody
    : block
    | SEMI
    ;

typeTypeOrVoid
    : typeType
    | VOID
    ;

genericMethodDeclaration
    : typeParameters methodDeclaration
    ;

genericConstructorDeclaration
    : typeParameters constructorDeclaration
    ;

constructorDeclaration
    : IDENTIFIER formalParameters (THROWS qualifiedNameList)? constructorBody=block
    ;

fieldDeclaration
    : typeType variableDeclarators SEMI
    ;

interfaceBodyDeclaration
    : modifier* interfaceMemberDeclaration
    | SEMI
    ;

interfaceMemberDeclaration
    : constDeclaration
    | interfaceMethodDeclaration
    | genericInterfaceMethodDeclaration
    | interfaceDeclaration
    | annotationTypeDeclaration
    | classDeclaration
    | enumDeclaration
    ;

constDeclaration
    : typeType constantDeclarator (COMMA constantDeclarator)* SEMI
    ;

constantDeclarator
    : IDENTIFIER (LBRACK RBRACK)* ASSIGN variableInitializer
    ;

// see matching of [] comment in methodDeclaratorRest
// methodBody from Java8
interfaceMethodDeclaration
    : interfaceMethodModifier* (typeTypeOrVoid | typeParameters annotation* typeTypeOrVoid)
      IDENTIFIER formalParameters (LBRACK RBRACK)* (THROWS qualifiedNameList)? methodBody
    ;

// Java8
interfaceMethodModifier
    : annotation
    | PUBLIC
    | ABSTRACT
    | DEFAULT
    | STATIC
    | STRICTFP
    ;

genericInterfaceMethodDeclaration
    : typeParameters interfaceMethodDeclaration
    ;

variableDeclarators
    : variableDeclarator (COMMA variableDeclarator)*
    ;

variableDeclarator
    : variableDeclaratorId (ASSIGN variableInitializer)?
    ;

variableDeclaratorId
    : IDENTIFIER (LBRACK RBRACK)*
    ;

variableInitializer
    : arrayInitializer
    | expression
    ;

arrayInitializer
    : LBRACE (variableInitializer (COMMA variableInitializer)* (COMMA)? )? RBRACE
    ;

classOrInterfaceType
    : IDENTIFIER typeArguments? (DOT IDENTIFIER typeArguments?)*
    ;

typeArgument
    : typeType
    | QUESTION ((EXTENDS | SUPER) typeType)?
    ;

qualifiedNameList
    : qualifiedName (COMMA qualifiedName)*
    ;

formalParameters
    : LPAREN formalParameterList? RPAREN
    ;

formalParameterList
    : formalParameter (COMMA formalParameter)* (COMMA lastFormalParameter)?
    | lastFormalParameter
    ;

formalParameter
    : variableModifier* typeType variableDeclaratorId
    ;

lastFormalParameter
    : variableModifier* typeType ELLIPSIS variableDeclaratorId
    ;

qualifiedName
    : IDENTIFIER (DOT IDENTIFIER)*
    ;

literal
    : integerLiteral
    | floatLiteral
    | CHAR_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    | NULL_LITERAL
    ;

integerLiteral
    : DECIMAL_LITERAL
    | HEX_LITERAL
    | OCT_LITERAL
    | BINARY_LITERAL
    ;

floatLiteral
    : FLOAT_LITERAL
    | HEX_FLOAT_LITERAL
    ;

// ANNOTATIONS

annotation
    : AT qualifiedName (LPAREN ( elementValuePairs | elementValue )? RPAREN)?
    ;

elementValuePairs
    : elementValuePair (COMMA elementValuePair)*
    ;

elementValuePair
    : IDENTIFIER ASSIGN elementValue
    ;

elementValue
    : expression
    | annotation
    | elementValueArrayInitializer
    ;

elementValueArrayInitializer
    : LBRACE (elementValue (COMMA elementValue)*)? (COMMA)? RBRACE
    ;

annotationTypeDeclaration
    : AT INTERFACE IDENTIFIER annotationTypeBody
    ;

annotationTypeBody
    : LBRACE (annotationTypeElementDeclaration)* RBRACE
    ;

annotationTypeElementDeclaration
    : modifier* annotationTypeElementRest
    | SEMI // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest
    : typeType annotationMethodOrConstantRest SEMI
    | classDeclaration SEMI?
    | interfaceDeclaration SEMI?
    | enumDeclaration SEMI?
    | annotationTypeDeclaration SEMI?
    ;

annotationMethodOrConstantRest
    : annotationMethodRest
    | annotationConstantRest
    ;

annotationMethodRest
    : IDENTIFIER LPAREN RPAREN defaultValue?
    ;

annotationConstantRest
    : variableDeclarators
    ;

defaultValue
    : DEFAULT elementValue
    ;

// STATEMENTS / BLOCKS

block
    : LBRACE blockStatement* RBRACE
    ;

blockStatement
    : localVariableDeclaration SEMI
    | statement
    | localTypeDeclaration
    ;

localVariableDeclaration
    : variableModifier* typeType variableDeclarators
    ;

localTypeDeclaration
    : classOrInterfaceModifier*
      (classDeclaration | interfaceDeclaration)
    | SEMI
    ;

statement
    : blockLabel=block                                                              # BlocksStatement
    | ASSERT expression (COLON expression)? SEMI                                    # AssertStatement
    | IF parExpression statement (ELSE statement)?                                  # IfStatement
    | FOR LPAREN forControl RPAREN statement                                        # ForStatement
    | WHILE parExpression statement                                                 # WhileStatement
    | DO statement WHILE parExpression SEMI                                         # DoStatement
    | TRY block (catchClause+ finallyBlock? | finallyBlock)                         # TryStatement
    | TRY resourceSpecification block catchClause* finallyBlock?                    # TryResourceStatement
    | SWITCH parExpression LBRACE switchBlockStatementGroup* switchLabel* RBRACE    # SwitchStatement
    | SYNCHRONIZED parExpression block                                              # SynchronizedStatement
    | RETURN expression? SEMI                                                       # ReturnStatement
    | THROW expression SEMI                                                         # ThrowStatement
    | BREAK IDENTIFIER? SEMI                                                        # BreakStatement
    | CONTINUE IDENTIFIER? SEMI                                                     # ContinueStatement
    | SEMI                                                                          # EmptyStatement
    | expression SEMI                                                               # ExpressionStatement
    | identifierLabel=IDENTIFIER COLON statement                                    # LabeledStatement
    ;

catchClause
    : CATCH LPAREN variableModifier* catchType IDENTIFIER RPAREN block
    ;

catchType
    : qualifiedName (BITOR qualifiedName)*
    ;

finallyBlock
    : FINALLY block
    ;

resourceSpecification
    : LPAREN resources SEMI? RPAREN
    ;

resources
    : resource (SEMI resource)*
    ;

resource
    : variableModifier* classOrInterfaceType variableDeclaratorId ASSIGN expression
    ;

/** Matches cases then statements, both of which are mandatory.
 *  To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    : switchLabel+ blockStatement+
    ;

switchLabel
    : CASE (constantExpression=expression | enumConstantName=IDENTIFIER) COLON
    | DEFAULT COLON
    ;

forControl
    : enhancedForControl
    | forInit? SEMI expression? SEMI forUpdate=expressionList?
    ;

forInit
    : localVariableDeclaration
    | expressionList
    ;

enhancedForControl
    : variableModifier* typeType variableDeclaratorId COLON expression
    ;

// EXPRESSIONS

parExpression
    : LPAREN expression RPAREN
    ;

expressionList
    : expression (COMMA expression)*
    ;

methodCall
    : IDENTIFIER LPAREN expressionList? RPAREN
    | THIS LPAREN expressionList? RPAREN
    | SUPER LPAREN expressionList? RPAREN
    ;

expression
    : primary                                                               # PrimaryExpression
    | expression bop=DOT
      ( IDENTIFIER
      | methodCall
      | THIS
      | NEW nonWildcardTypeArguments? innerCreator
      | SUPER superSuffix
      | explicitGenericInvocation
      )                                                                     # DotExpression
    | expression LBRACK expression RBRACK                                   # ArrayExpression
    | methodCall                                                            # MethodCallExpression
    | NEW creator                                                           # NewExpression
    | LPAREN typeType RPAREN expression                                     # CastExpression
    | expression postfix=(INC | DEC)                                        # PostfixExpression
    | prefix=(ADD | SUB | INC | DEC) expression                             # PrefixExpression
    | prefix=(TILDE | BANG) expression                                      # NotExpression
    | expression bop=(ADD | SUB | MUL | DIV | MOD) expression               # ArithExpression
    | expression (LSHIFT | RSHIFT | URSHIFT) expression                     # ShiftExpression
    | expression bop=(LE | GE | GT | LT) expression                         # ComparisonExpression
    | expression bop=INSTANCEOF typeType                                    # InstanceofExpression
    | expression bop=(EQUAL | NOTEQUAL) expression                          # EqualityExpression
    | expression bop=(BITAND | BITOR | CARET) expression                    # BitwiseExpression
    | expression bop=(AND | OR) expression                                  # LogicalExpression
    | <assoc=right> expression bop=QUESTION expression COLON expression     # TeneryExpression
    | <assoc=right> expression
      bop=(ASSIGN | ADD_ASSIGN | SUB_ASSIGN | MUL_ASSIGN | DIV_ASSIGN
            | AND_ASSIGN | OR_ASSIGN | XOR_ASSIGN | RSHIFT_ASSIGN
            | URSHIFT_ASSIGN | LSHIFT_ASSIGN | MOD_ASSIGN)
      expression                                                            # AssignExpression
    | lambdaExpr                                                            # LambdaExpression
    | expression COLONCOLON typeArguments? IDENTIFIER                       # ExprRefExpression
    | typeType COLONCOLON (typeArguments? IDENTIFIER | NEW)                 # TypeRefExpression
    | classType COLONCOLON typeArguments? NEW                               # ClassRefExpression
    ;

// Java8
lambdaExpr
    : lambdaParameters ARROW lambdaBody
    ;

// Java8
lambdaParameters
    : IDENTIFIER
    | LPAREN formalParameterList? RPAREN
    | LPAREN IDENTIFIER (COMMA IDENTIFIER)* RPAREN
    ;

// Java8
lambdaBody
    : expression
    | block
    ;

primary
    : LPAREN expression RPAREN
    | THIS
    | SUPER
    | literal
    | IDENTIFIER
    | typeTypeOrVoid DOT CLASS
    | nonWildcardTypeArguments (explicitGenericInvocationSuffix | THIS arguments)
    ;

classType
    : (classOrInterfaceType DOT)? annotation* IDENTIFIER typeArguments?
    ;

creator
    : nonWildcardTypeArguments createdName classCreatorRest
    | createdName (arrayCreatorRest | classCreatorRest)
    ;

createdName
    : IDENTIFIER typeArgumentsOrDiamond? (DOT IDENTIFIER typeArgumentsOrDiamond?)*
    | primitiveType
    ;

innerCreator
    : IDENTIFIER nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    : LBRACK (RBRACK (LBRACK RBRACK)* arrayInitializer | expression RBRACK (LBRACK expression RBRACK)* (LBRACK RBRACK)*)
    ;

classCreatorRest
    : arguments classBody?
    ;

explicitGenericInvocation
    : nonWildcardTypeArguments explicitGenericInvocationSuffix
    ;

typeArgumentsOrDiamond
    : LT GT
    | typeArguments
    ;

nonWildcardTypeArgumentsOrDiamond
    : LT GT
    | nonWildcardTypeArguments
    ;

nonWildcardTypeArguments
    : LT typeList GT
    ;

typeList
    : typeType (COMMA typeType)*
    ;

typeType
    : annotation? (classOrInterfaceType | primitiveType) (LBRACK RBRACK)*
    ;

primitiveType
    : BOOLEAN
    | CHAR
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
    ;

typeArguments
    : LT typeArgument (COMMA typeArgument)* GT
    ;

superSuffix
    : arguments
    | DOT IDENTIFIER arguments?
    ;

explicitGenericInvocationSuffix
    : SUPER superSuffix
    | IDENTIFIER arguments
    ;

arguments
    : LPAREN expressionList? RPAREN
    ;
