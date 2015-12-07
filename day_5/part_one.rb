#!/usr/bin/ruby

File.open("input.txt", 'r') do |f|

  nice_string = 0 #Result 258

  f.each_line do |line|

    if !(line =~ /ab|cd|pq|xy/) and line =~ /[aeiou].*[aeiou].*[aeiou]/ and line =~ /(?=(\w)\1)/
      nice_string += 1
      puts line
    end

  end
  puts nice_string
end