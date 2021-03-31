#include <catch2/catch.hpp>

#include "student.h"

TEST_CASE("Student Test", "[student]") {
  SECTION("Init with no modules") {
    Student stud;
    REQUIRE(stud.GetModuleCount() == 0);
  }

  SECTION("Add one module and check count") {
    Student stud;
    REQUIRE(stud.AddModule(Module(3, "bsp")));
    REQUIRE(stud.GetModuleCount() == 1);
  }

  SECTION("Add more modules then allowed") {
    Student stud;
    REQUIRE(stud.AddModule(Module(1, "bsp1")));
    REQUIRE(stud.AddModule(Module(2, "bsp2")));
    REQUIRE(stud.AddModule(Module(3, "bsp3")));
    REQUIRE(stud.AddModule(Module(4, "bsp4")));
    REQUIRE(stud.GetModuleCount() == 4);

    REQUIRE(stud.AddModule(Module(5, "bsp5")) == false);
  }

  SECTION("Add and remove one module") {
    Student stud;
    REQUIRE(stud.AddModule(Module(3, "bsp")));
    REQUIRE(stud.GetModuleCount() == 1);
    REQUIRE(stud.RemoveModule(0));
    REQUIRE(stud.GetModuleCount() == 0);
  }

  SECTION("Modules are reorganized after remove") {
    auto mod1 = Module(1, "bsp1");
    auto mod2 = Module(1, "bsp2");
    auto mod3 = Module(1, "bsp3");
    auto mod4 = Module(1, "bsp4");

    Student stud;
    REQUIRE(stud.AddModule(mod1));
    REQUIRE(stud.AddModule(mod2));
    REQUIRE(stud.AddModule(mod3));
    REQUIRE(stud.AddModule(mod4));

    REQUIRE(stud.RemoveModule(1));
    REQUIRE(stud.GetModuleCount() == 3);

    REQUIRE(stud.GetModule(0).name_ == mod1.name_);
    REQUIRE(stud.GetModule(1).name_ == mod3.name_);
    REQUIRE(stud.GetModule(2).name_ == mod4.name_);
  }
}
