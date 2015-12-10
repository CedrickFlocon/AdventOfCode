#!/usr/bin/ruby
#Result 53

nice_string = 0

File.open('input.txt', 'r') do |f|
  f.each_line do |line|

    if line =~ /(\w{2}).*\1/ and line =~ /(\w).\1/
      nice_string += 1
      puts line
    end

  end

  puts nice_string
end

