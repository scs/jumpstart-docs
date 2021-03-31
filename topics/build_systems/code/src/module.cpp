#include "module.h"

Module::Module()
    : credits_(0)
{}

Module::Module(int credits, const char* name)
  : credits_(credits)
  , name_(name)
{}
