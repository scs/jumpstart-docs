#pragma once

#include <string>

struct Module {
  Module();
  Module(int credits, const char* name);

  int credits_;
  std::string name_;
};
