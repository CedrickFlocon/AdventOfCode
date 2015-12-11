#!/usr/bin/ruby
#Result hepxxyzz

input = 'hepxcrrq'

def is_valid(password)
  is_valid = false
  (0..(password.size - 3 )).each { |i|
    if password[i].next == password[i + 1] and password[i + 1].next == password[i + 2]
      is_valid = true
    end
  }

  is_valid = is_valid && password =~ /^[^iol]+$/
  double_char = password.scan(/(\w)\1/)
  is_valid && double_char.size > 0 && password =~ /([^#{double_char[0]}])\1/
end

i = input.size - 1
until is_valid(input)
  if input[i] == 'z'
    input[i] = 'a'
    i-=1
  else
    input[i] = input[i].next
    i = input.size - 1
  end
end
puts input
