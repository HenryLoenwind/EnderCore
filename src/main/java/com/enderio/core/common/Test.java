package com.enderio.core.common;

//JavaPerformance.java
import java.util.Random;

import com.enderio.core.common.interfaces.IComparatorOutput;


public class Test {
  private final static long iterations = 1000000000;
  
  static C C1 = new C(A.class);  
  static C C2 = new C(B.class);  

  static Random r = new Random();
  private static C randomCase()  {
    int randomPositiveNumber = r.nextInt(2); 
        
    switch (randomPositiveNumber) {
    case 0:
      return C1;
    case 1:
      return C2;
    default:
      throw new Error();
    }
  }
  
  private static void evaluateUncached() {
    long start = System.currentTimeMillis();
    for (long i = 0; i<iterations; i++) {
      randomCase().do1();
    }
    long time = System.currentTimeMillis() - start;
    System.out.println("uncached: "+time+"ms/100,000,000 iterations");
  }

  private static void evaluateCached() {
    long start = System.currentTimeMillis();
    for (long i = 0; i<iterations; i++) {
      randomCase().do2();
    }
    long time = System.currentTimeMillis() - start;
    System.out.println("cached: "+time+"ms/100,000,000 iterations");
  }

  private static void evaluateInstanceOfUncached() {
    long start = System.currentTimeMillis();
    for (long i = 0; i<iterations; i++) {
      randomCase().do3();
    }
    long time = System.currentTimeMillis() - start;
    System.out.println("instanceof (uncached): "+time+"ms/100,000,000 iterations");
  }


  public static void main(String[] args) {
    evaluateUncached();
    evaluateCached();
    evaluateInstanceOfUncached();
    evaluateUncached();
    evaluateCached();
    evaluateInstanceOfUncached();
    evaluateUncached();
    evaluateCached();
    evaluateInstanceOfUncached();
    evaluateUncached();
    evaluateCached();
    evaluateInstanceOfUncached();
    evaluateUncached();
    evaluateCached();
    evaluateInstanceOfUncached();
    }
  }

