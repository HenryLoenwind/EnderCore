package com.enderio.core.common;

import com.enderio.core.common.interfaces.IComparatorOutput;

public class C {

  
  boolean haveChecked = false;
  boolean isInterface = false;
  final Class<?> clazz;
  final Object o;
  
  public C(Class<?> clazz) {
    super();
    this.clazz = clazz;
    try {
      o = clazz.newInstance();
    } catch (Exception e) {
      throw new Error();
    }
  }

  int h=0,j=0;
  
  void do1() {
    if (IComparatorOutput.class.isAssignableFrom(clazz)) {
      h++;
    } else {
      j++;
    }
  }

  void do2() {
    if (!haveChecked) {
      isInterface = IComparatorOutput.class.isAssignableFrom(clazz);
      haveChecked = true;
    }
    if (isInterface) {
      h++;
    } else {
      j++;
    }
  }

  void do3() {
    if (o instanceof IComparatorOutput) {
      h++;
    } else {
      j++;
    }
  }
}
