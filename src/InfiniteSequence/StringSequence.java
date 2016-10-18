/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfiniteSequence;

import java.math.BigInteger;

/**
 *
 * @author Alexey
 */
public class StringSequence {
    public int length;
    
    public BigInteger position;
    public int shift;

    public int sequence[];
    
    public StringSequence(String str) {
        this.length = str.length();
        this.sequence = new int[length];

        BigInteger src[] = new BigInteger[2];
        src[0] = new BigInteger(str);

        for (int i = this.length-1; i >=0; i--) {
            src = src[0].divideAndRemainder(BigInteger.TEN);

            this.sequence[i] = src[1].intValue();
        }
    }

    public StringSequence(int newSequence[]) {
        this.length = newSequence.length;
        this.sequence = newSequence;
    }

    public BigInteger getNum(int start, int length) {
    	BigInteger retval = BigInteger.ZERO;

    	for(int i = 0; i < length; i++) {
            retval = retval.multiply(BigInteger.TEN);
            retval = retval.add(BigInteger.valueOf(this.sequence[start+i]));
    	}

    	return retval;
    }


    public boolean allZero(int start, int length) {
        for(int i = 0; i < length; i++)
            if (this.sequence[start+i] != 0) return false;
        
        return true;
    }
    public boolean allNine(int start, int length) {
    	for(int i = 0; i < length; i++)
    		if (this.sequence[start+i] != 9) return false;
    	
    	return true;
    }

	public boolean prefix(BigInteger pivot, int shift) {
		if (shift == 0)
			return true;
		else {
			BigInteger start = this.getNum(0, shift);
			pivot = pivot.subtract(BigInteger.ONE);

			int modNum = (int)Math.pow(10, shift);

			pivot = pivot.remainder(BigInteger.valueOf(modNum) );

			return start.equals(pivot);
		}
    }

	public boolean postfix(BigInteger pivot, int step,  int shift) {
		if (shift == length)
			return true;
		else {
			BigInteger finish = this.getNum(shift, (this.length - shift) );
			pivot = pivot.add(BigInteger.ONE);

			int modNum = (int)Math.pow(10, (step + shift - this.length) );

			pivot = pivot.divide(BigInteger.valueOf(modNum) );

			return finish.equals(pivot);
		}
    }


    public boolean someSequence(int step, int shift) {
    	BigInteger pivot = this.getNum(shift, step);

        BigInteger position = pivot;

        if (prefix(pivot, shift) == false)
        	return false;

        int i;
        BigInteger next = pivot;

		for (i = step + shift; (i + step) <= length; i+=step) {

    		pivot = this.getNum(i-step, step);
			pivot = pivot.add(BigInteger.ONE);

    		if (this.allNine(i-step, step) == true) {
    			step++;
    		}

            if ((i + step) <= length) {
                next = this.getNum(i, step);
            
                if (pivot.equals(next) != true) {
                    return false;
                }
            
            }
            else {
                if (postfix(next, step, i) == false)
                return false;
            }
    	}

		if (postfix(next, step, i) == false)
        	return false;

        if (this.position == null) {
            this.position = position;
            this.shift = -shift;
        }
        else if (this.position.compareTo(position) == 1) {
            this.position = position;
            this.shift = -shift;
        }

    	return true;
    }

    public void uniqueNum() {
        int minNum = 10 ;

        for (int i = 0; i < this.length; i++) {
            if ((this.sequence[i] > 0) && (this.sequence[i] < minNum)) minNum = this.sequence[i];
        }

        BigInteger result ;

        for (int shift = 0; shift < this.length; shift++) {
            if (this.sequence[shift] == minNum) {
                result = this.getNum(shift, this.length - shift);

                int modNum = (int)Math.pow(10, shift);

                result = result.multiply(BigInteger.valueOf(modNum) );

                result = result.add(this.getNum(0, shift));

                if (this.position == null) {
                    this.position = result;
                    this.shift = shift;
                //    this.shift = this.length-shift;
                }
                else if (this.position.compareTo(result) == 1) {
                    this.position = result;
                    this.shift = shift;
                //    this.shift = this.length-shift;
                }
            }
        }
    }

    public void twoMum(int step, int shift) {
        if (this.sequence[0] == this.sequence[this.length-1]) {
            int extSequence[] = new int[this.length+shift];

            System.arraycopy(this.sequence, 0, extSequence, 0, this.length);

            extSequence[this.length - 1 + shift] = this.sequence[shift] + 1;

            for (int i = shift - 1; i > 0; i--) {
                extSequence[this.length + i - 1] = this.sequence[i] + extSequence[this.length + i]/10;
                extSequence[this.length + i] = extSequence[this.length + i]%10;
            }

            extSequence[this.length - 1 + shift] = extSequence[this.length - 1 + shift]%10;

            StringSequence extendedSequence = new StringSequence(extSequence);

            if (extendedSequence.someSequence(step, shift+1) == true) {
                if (this.position == null) {
                    this.position = extendedSequence.position;
                    this.shift = extendedSequence.shift;
                }
                else if (this.position.compareTo(extendedSequence.position) == 1) {
                    this.position = extendedSequence.position;
                    this.shift = extendedSequence.shift;
                }
            }
        }
        if (this.sequence[shift] == 9) {
            int numNine = 0;
            int numZero = 0;

            int endFirst = shift;

            while (this.sequence[endFirst] == 9) {
                numNine++;
                endFirst++;
            }

            for (int i = this.length - 1; i > 0; i--) {
                if (this.sequence[i] == 0)
                numZero++;
            }

            int extendedLength = this.length + numNine - numZero;

            step = extendedLength -  endFirst;

            if ( (numNine - numZero) > 0) {
                int extSequence[] = new int[extendedLength];

                System.arraycopy(this.sequence, 0, extSequence, 0, this.length);

                for (int i = 0; i  < (numNine - numZero); i++) {
                    extSequence[this.length + i] = 0;
                }

                StringSequence extendedSequence = new StringSequence(extSequence);
  
                if (extendedSequence.someSequence(step, shift + numNine - numZero) == true) {
                    if (this.position == null) {
                        this.position = extendedSequence.position;
                        this.shift = extendedSequence.shift;
                    }
                    else if (this.position.compareTo(extendedSequence.position) == 1) {
                        this.position = extendedSequence.position;
                        this.shift = extendedSequence.shift;
                    }
                }
            }
        }
    }

    public BigInteger calcPosition() {
        BigInteger src = this.position;

        String exponent = this.position.toString();

        BigInteger rslt = BigInteger.ONE ;
        BigInteger index = BigInteger.TEN ;

        int i = 0;

        for (i = 0; i < (exponent.length() - 1); i++ ) {
            index = BigInteger.TEN;
            index = index.pow(i);
            index = index.multiply(BigInteger.valueOf(9));
            index = index.multiply(BigInteger.valueOf(i+1));
            rslt = rslt.add(index);
        }

        index = BigInteger.TEN;
        index = index.pow(i);

        src = src.subtract(index);
        src = src.multiply(BigInteger.valueOf(exponent.length()));

        rslt = rslt.add(src);
        rslt = rslt.add(BigInteger.valueOf(this.shift));
  
        return rslt;
    }

    public BigInteger findPosition() {
        if ((this.allNine(0, this.length) == false) && (this.allZero(0, this.length) == false) ) {
        	for(int step = 1; step < this.length; step++) {
        		for (int shift = 0; shift < step; shift ++) {
        			if ((step + shift) <= this.length) {
        				this.someSequence(step, shift);
        			}
                    this.twoMum(step, shift);
        		}
        	}
        }
        else if (this.allNine(0, this.length) == true) {
            this.position = this.getNum(0, this.length);
        }
        else if (this.allZero(0, this.length) == true) {
            this.position = BigInteger.TEN;
            this.position = this.position.pow(this.length);
        }

        this.uniqueNum();

        return this.calcPosition();
    }
}
