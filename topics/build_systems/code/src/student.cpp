#include "student.h"

#include <cassert>

Student::Student()
  : module_count_(0)
{}

Module Student::GetModule(size_t index) {
  assert(index < module_count_);
  return modules_[index];
}

bool Student::AddModule(Module new_module) {
  if (module_count_ >= kMaxModules) {
    return false;
  }

  modules_[module_count_] = new_module;
  module_count_++;

  return true;
}

bool Student::RemoveModule(size_t index) {
  if (index >= module_count_) {
    return false;
  }

  for (size_t i = index; i < kMaxModules - 1; ++i) {
    modules_[i] = modules_[i + 1];
  }

  module_count_--;

  return true;
}
