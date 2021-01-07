package com.mzielinski.advent.of.code.day25

import spock.lang.Specification
import spock.lang.Unroll

class Day25Test extends Specification {

    @Unroll
    def 'should find encryption key #encryptionKey for door #doorPublicKey and card #cardPublicKey'() {

        expect:
        new Day25().findEncryptionKey(cardPublicKey, doorPublicKey) == encryptionKey

        where:
        doorPublicKey | cardPublicKey || encryptionKey
        17807724      | 5764801       || 14897079
        2084668       | 3704642       || 42668
    }
}
