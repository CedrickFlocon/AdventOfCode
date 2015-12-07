#!/usr/bin/ruby

nice_string = 0 #Result 53

File.open("input.txt", 'r') do |f|
  f.each_line do |line|

    if line =~ /(\w{2}).*\1/ and line =~ /(\w).\1/
      nice_string += 1
      puts line
    end

  end

  puts nice_string
end

