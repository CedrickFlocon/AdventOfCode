#!/usr/bin/ruby
#Result 156366

sum = 0

File.readlines('input.txt').each { |line|
  line.scan(/-?\d+/).each { |number| sum +=number.to_i }
}
puts sum