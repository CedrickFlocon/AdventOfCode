#!/usr/bin/ruby
# Result 2117

$all_char = 0
$escape_char = 0

File.readlines('input.txt').each { |line|
  $all_char += line.strip.size
}

File.readlines('input.txt').each { |line|
  $escape_char += line.strip.inspect.size
}

puts $escape_char - $all_char