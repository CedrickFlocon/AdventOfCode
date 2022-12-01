#!/usr/bin/ruby
# Result 1371

$all_char = 0
$displayed_char = 0

File.readlines('input.txt').each { |line|
  $all_char += line.strip.size
}

File.readlines('input.txt').each { |line|
  $displayed_char += eval(line).size
}

puts $all_char - $displayed_char