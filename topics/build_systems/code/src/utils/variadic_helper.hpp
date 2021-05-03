#pragma once

/// Let shut up the compiler warnings about not used variables.
template<typename... TArgs>
void ignore_unused(const TArgs&...) {}


/** @defgroup IndexSequence Type that can be statically instantiated to contain all indices of a tuple (or similar)
 *  in range [0, End). Such a type can be used to iterate over a tuples values or expand a tuple access.
 *  See: http://stackoverflow.com/questions/16387354/template-tuple-calling-a-function-on-each-element
 *  @{ */

template<size_t... End>
struct seq {
};

template<size_t N, size_t... End>
struct gen_seq: gen_seq<N - 1, N - 1, End...> {
};

template<size_t... End>
struct gen_seq<0, End...> : seq<End...> {
};

/** @}*/
