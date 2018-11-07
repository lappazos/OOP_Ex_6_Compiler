package oop.ex6.main.Ifwhile;

import oop.ex6.main.Scope;

import java.util.List;

/**
 * class indicates if/while scopes blocks
 *
 * @author lioraryepaz, shahar.mazia
 */

class IfWhile extends Scope {
    /**
     * Constructor
     *
     * @param scope      parent scope
     * @param BlockLines list of scope lines
     */
    IfWhile(Scope scope, List<String> BlockLines) {
        super(scope, BlockLines);

    }
}
