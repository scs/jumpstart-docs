#include "student.h"

#include <iostream>

int main() {
  Student stud;

  stud.AddModule(Module(3, "prcpp"));
  stud.AddModule(Module(3, "sysad"));

  for (size_t i = 0; i < stud.GetModuleCount(); ++i) {
    auto m = stud.GetModule(i);
    std::cout << "Module: " << m.name_ << ", ECTS: " << m.credits_ << std::endl;
  }

  return EXIT_SUCCESS;
}
