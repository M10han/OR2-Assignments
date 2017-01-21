def congruential(a, m, x):
    x_0 = []
    x_0.append(x)
    for i in range(1, 6):
        z = (a * x_0[i - 1]) % m
        x_0.append(z)
        if x_0[i] == x_0[0]:
            break
    print 'Value of X0 =', x
    print 'Value of a = ', a
    print 'Value of m =', m
    print 'Numbers in series'
    for j in range(i):
        if j <= i:
            print x_0[j]


congruential(11, 16, 7)
congruential(11, 16, 8)
congruential(7, 16, 7)
congruential(8, 16, 7)