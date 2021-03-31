#pragma once

#include <cstddef>

#include "module.h"

struct Student {
  static constexpr size_t kMaxModules = 4;

  Student();

  size_t GetModuleCount() { return module_count_; }
  Module GetModule(size_t index);

  bool AddModule(Module new_module);
  bool RemoveModule(size_t index);

  Module modules_[kMaxModules];
  size_t module_count_;
};
