package sayan.apps.numplex

import java.util.Locale
import kotlin.math.pow
import kotlin.math.sqrt

fun isEven(number: Long): Boolean = number % 2 == 0L

fun digitCount(number: Long) = number.toString().length

fun digitSum(number: Long): Long = number.toString().map { it - '0' }.sum().toLong()

fun digitProduct(number: Long): Long {
    var product: Long = 1
    var num = number
    while (num != 0L) {
        val digit = num % 10
        product *= digit
        num /= 10
    }
    return product
}

fun reverse(number: Long) = number.toString().reversed().toLong()

fun factorial(n: Long): Long {
    if (n == 0L || n == 1L) {
        return 1L
    }
    var result = 1L
    for (i in 2..n) {
        result *= i
    }
    return result
}

fun isPerfectSquare(number: Long): Boolean {
    val sqrt = sqrt(number.toDouble()).toLong()
    return sqrt * sqrt == number
}

fun isPalindrome(number:Long) = number==reverse(number)

fun decimalToBin(n: Long): String = n.toString(2)

fun decimalToOct(n: Long): String = n.toString(8)

fun decimalToHex(n: Long): String = n.toString(16).uppercase(Locale.getDefault())

fun isPrimeNumber(n: Long): Boolean {
    if (n <= 1) return false
    if (n <= 3) return true
    if (n % 2 == 0L || n % 3 == 0L) return false
    var i: Long = 5
    while (i * i <= n) {
        if (n % i == 0L || n % (i + 2) == 0L) return false
        i += 6
    }
    return true
}

fun primeFactors(n: Long): List<Long> {
    var num = n
    val factors = mutableListOf<Long>()
    while (num % 2 == 0L) {
        factors.add(2)
        num /= 2
    }
    var i: Long = 3
    while (i * i <= num) {
        while (num % i == 0L) {
            factors.add(i)
            num /= i
        }
        i += 2
    }
    if (num > 2) {
        factors.add(num)
    }
    return factors
}

fun primeFactorization(n: Long): String {
    val factors = primeFactors(n)
    if (factors.isEmpty()) return "$n has no prime factors"
    val factorCount = mutableMapOf<Long, Int>()
    for (factor in factors) {
        factorCount[factor] = factorCount.getOrDefault(factor, 0) + 1
    }
    return factorCount.entries.joinToString(separator = " \u00D7 ") { "${it.key}^${it.value}" }
}

fun factors(n: Long): List<Long> {
    val factors = mutableListOf<Long>()
    for (i in 1..sqrt(n.toDouble()).toLong()) {
        if (n % i == 0L) {
            factors.add(i)
            if (i != n / i) {
                factors.add(n / i)
            }
        }
    }
    factors.sort()
    return factors
}

fun isNivenNumber(number: Long): Boolean = number % digitSum(number) == 0L

fun isEmirpNumber(number: Long): Boolean = if (isPrimeNumber(number)) (isPrimeNumber(reverse(number))) else false

fun isAbundantNumber(number: Long): Long = (factors(number).toSet().sum() - number) - number

fun isTechNumber(number: Long) : Boolean {
    if (digitCount(number) % 2 != 0)
        return false
    val numberString = number.toString()
    val halfLength = numberString.length / 2
    val firstHalf = numberString.substring(0, halfLength)
    val secondHalf = numberString.substring(halfLength)
    val sumOfHalves = firstHalf.toLong() + secondHalf.toLong()
    val squareOfSum = sumOfHalves * sumOfHalves
    return squareOfSum == number
}

fun isDisariumNumber(number: Long): Boolean {
    var num = number
    var sum = 0L
    var position = 1L
    while (num > 0) {
        val digit = num % 10
        sum += digit.toDouble().pow(position.toDouble()).toLong()
        position++
        num /= 10
    }
    return sum == number
}

fun isPronicNumber(number: Long): Boolean {
    var x = 0L
    while (x * (x + 1) <= number) {
        if (x * (x + 1) == number) {
            return true
        }
        x++
    }
    return false
}

fun isAutomorphicNumber(number: Long): Boolean {
    val square = number * number
    val numberString = number.toString()
    val squareString = square.toString()
    return squareString.endsWith(numberString)
}

fun isKaprekarNumber(number: Long): Boolean {
    val square = number * number
    var n = square
    var digits = 0L
    while (n != 0L) {
        digits++
        n /= 10
    }
    var divisor = 1L
    for (i in 1 until digits) {
        divisor *= 10
        val secondPart = square % divisor
        val firstPart = square / divisor
        if (secondPart != 0L && firstPart + secondPart == number) {
            return true
        }
    }
    return false
}


fun isSpecialNumber(number: Long): Boolean {
    var num = number
    var sum = 0L
    while (num > 0) {
        val digit = num % 10
        sum += factorial(digit)
        num /= 10
    }
    return sum == number
}


fun isLucasNumber(number: Long): Boolean {
    var a = 2L
    var b = 1L
    if (number == a || number == b) {
        return true
    }
    while (b <= number) {
        val nextNumber = a + b
        if (nextNumber == number) {
            return true
        }
        a = b
        b = nextNumber
    }
    return false
}

fun isSmithNumber(number: Long): Boolean {
    if (isPrimeNumber(number)) return false
    var sum = 0L
    for (primeFactor in primeFactors(number)) {
        sum += if (primeFactor >= 10) {
            primeFactor.toString().map { it.toString().toLong() }.sum()
        } else {
            primeFactor
        }
    }
    return digitSum(number) == sum
}

fun isArmstrongNumber(number: Long): Boolean {
    var num = number
    val numberOfDigits = num.toString().length
    var sum = 0L
    while (num > 0) {
        val digit = num % 10
        sum += digit.toDouble().pow(numberOfDigits.toDouble()).toLong()
        num /= 10
    }
    return sum == number
}

fun isFibonacciNumber(number: Long): Boolean {
    return isPerfectSquare(5 * number * number + 4L) || isPerfectSquare(5 * number * number - 4L)
}

fun rotateNumber(number: Long): Long {
    val numberStr = number.toString()
    return (numberStr.substring(1) + numberStr[0]).toLong()
}

fun isCircularPrimeNumber(number: Long): Boolean {
    var rotatedNumber = number
    repeat(number.toString().length) {
        if (!isPrimeNumber(rotatedNumber)) {
            return false
        }
        rotatedNumber = rotateNumber(rotatedNumber)
    }
    return true
}

fun isFermatNumber(number: Long) = setOf(3L, 5L, 17L, 257L, 65537L, 4294967297L).contains(number)

fun isUglyNumber(number: Long): Boolean {
    if (number <= 0) return false
    val primeFactors = primeFactors(number)
    for (factor in primeFactors) {
        if (factor != 2L && factor != 3L && factor != 5L) {
            return false
        }
    }
    return true
}

fun isNeonNumber(number: Long) = number==digitSum(number*number)

fun isSpyNumber(number: Long) = digitSum(number)==digitProduct(number)

fun isHappyNumber(number: Long): Boolean {
    var num = number
    val seen = HashSet<Long>()
    while (num != 1L && !seen.contains(num)) {
        seen.add(num)
        var sum = 0L
        while (num > 0) {
            val digit = num % 10
            sum += digit * digit
            num /= 10
        }
        num = sum
    }
    return num == 1L
}

fun isDuckNumber(number: Long): Boolean {
    var num = number
    var hasZero = false
    while (num != 0L) {
        val digit = num % 10
        if (digit == 0L && hasZero) {
            return true
        }
        if (digit == 0L) {
            hasZero = true
        }
        num /= 10
    }
    return false
}